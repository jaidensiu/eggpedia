package com.jaidensiu.eggpedia.ui.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.jaidensiu.eggpedia.app.Route
import com.jaidensiu.eggpedia.data.Egg
import com.jaidensiu.eggpedia.ui.shared.isAndroid
import com.plusmobileapps.konnectivity.Konnectivity
import eggpedia.composeapp.generated.resources.Res
import eggpedia.composeapp.generated.resources.back
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
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
    val konnectivity = remember { Konnectivity() }
    val isConnected by konnectivity.isConnectedState.collectAsState()

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
            .background(MaterialTheme.colors.background)
            .pointerInput(Unit) { detectTapGestures { focusManager.clearFocus() } }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 32.dp,
                    top = if (isAndroid) 32.dp else 54.dp,
                    end = 32.dp,
                    bottom = 24.dp
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = {
                        focusManager.clearFocus()
                        onClickBack()
                    },
                    border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.primary)
                ) {
                    Text(
                        text = stringResource(Res.string.back),
                        color = MaterialTheme.colors.primary
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                EggSearchTextField(
                    searchQuery = mutableStateOf(value = state.value.searchQuery),
                    onSearchQueryChange = viewModel::onSearchQueryChange,
                    focusRequester = focusRequester,
                    interactionSource = interactionSource,
                    route = route
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                if (!isConnected && route == Route.EggsList) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 18.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "There is no internet connection",
                                color = MaterialTheme.colors.onBackground
                            )
                        }
                    }
                } else if (state.value.eggs.isEmpty() && route == Route.SavedEggsList) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 18.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "You have no saved egg recipes",
                                color = MaterialTheme.colors.onBackground
                            )
                        }
                    }
                } else if (filteredEggs.isEmpty() && debouncedQuery.isNotBlank()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 18.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "There are no egg recipes for your search",
                                color = MaterialTheme.colors.onBackground
                            )
                        }
                    }
                } else if (filteredEggs.isNotEmpty()) {
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
                }
            }
        }
    }
}
