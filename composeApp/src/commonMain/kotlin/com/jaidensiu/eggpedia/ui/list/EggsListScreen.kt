@file:OptIn(ExperimentalMaterialApi::class)

package com.jaidensiu.eggpedia.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.jaidensiu.eggpedia.app.Route
import com.jaidensiu.eggpedia.data.Egg
import com.jaidensiu.eggpedia.ui.shared.isAndroid
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EggsListScreen(
    viewModel: EggsListViewModel = koinViewModel(),
    route: Route,
    onClickBack: () -> Unit,
    onSelectEgg: (Egg) -> Unit
) {
    val state = viewModel.state.collectAsState()
    val interactionSource = remember { MutableInteractionSource() }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var debouncedQuery by remember { mutableStateOf(state.value.searchQuery) }
    val filteredEggs = state.value.eggs.filter { it.name.startsWith(prefix = debouncedQuery, ignoreCase = true) }

    LaunchedEffect(debouncedQuery) {
        viewModel.onSearchQueryChange(debouncedQuery)
    }

    LaunchedEffect(state.value.searchQuery) {
        launch {
            delay(timeMillis = 250L)
            debouncedQuery = state.value.searchQuery
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getEggs(route = route)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) { detectTapGestures { focusManager.clearFocus() } }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = if (isAndroid) 32.dp else 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        focusManager.clearFocus()
                        onClickBack()
                    }
                ) {
                    Text(text = "Back")
                }
                Spacer(modifier = Modifier.width(12.dp))
                BasicTextField(
                    value = state.value.searchQuery,
                    onValueChange = { viewModel.onSearchQueryChange(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                        .focusable()
                        .clickable { focusRequester.requestFocus() },
                    singleLine = true,
                    interactionSource = interactionSource
                ) { innerTextField ->
                    TextFieldDefaults.OutlinedTextFieldDecorationBox(
                        value = state.value.searchQuery,
                        innerTextField = innerTextField,
                        enabled = true,
                        singleLine = true,
                        visualTransformation = VisualTransformation.None,
                        interactionSource = interactionSource,
                        placeholder = {
                            if (route == Route.EggsList) {
                                Text(
                                    text = "Search for a recipe",
                                    maxLines = 1
                                )
                            } else if (route == Route.SavedEggsList) {
                                Text(
                                    text = "Search for a saved recipe",
                                    maxLines = 1
                                )
                            }
                        },
                        contentPadding = if (state.value.searchQuery.isBlank()) {
                            if (isAndroid) PaddingValues(6.dp) else PaddingValues(5.dp)
                        } else {
                            if (isAndroid) PaddingValues(10.dp) else PaddingValues(9.dp)
                        }
                    )
                }

            }
            Spacer(modifier = Modifier.height(12.dp))
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                if (filteredEggs.isNotEmpty()) {
                    items(filteredEggs) { egg ->
                        EggListItem(
                            egg = egg,
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                focusManager.clearFocus()
                                onSelectEgg(egg)
                            }
                        )
                    }
                } else if (filteredEggs.isEmpty() && debouncedQuery.isNotBlank()) {
                    item {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "There are no egg recipes for your search")
                        }
                    }
                }
            }
        }
    }
}
