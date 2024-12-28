package com.jaidensiu.eggpedia.ui.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.jaidensiu.eggpedia.data.Egg
import com.jaidensiu.eggpedia.ui.shared.isAndroid
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EggDetailsScreen(
    viewModel: EggDetailsViewModel = koinViewModel(),
    egg: Egg,
    onClickBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(value = false) }
    val painter = rememberAsyncImagePainter(model = egg.imageUrl)

    LaunchedEffect(Unit) {
        viewModel.observeSavedStatus(egg.id.toString())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(
                start = 32.dp,
                top = if (isAndroid) 32.dp else 54.dp,
                end = 32.dp,
                bottom = 24.dp
            )
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            TextButton(
                onClick = { onClickBack() },
                border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.primary)
            ) {
                Text(
                    text = "Back",
                    color = MaterialTheme.colors.primary
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    if (state.isSaved) {
                        showDialog = true
                    } else {
                        viewModel.saveEggToLocal(egg)
                    }
                }
            ) {
                Text(
                    text = if (state.isSaved) "Remove" else "Save",
                    color = MaterialTheme.colors.background
                )
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(text = egg.name)
                        Image(
                            painter = painter,
                            contentDescription = ""
                        )
                        Text(text = "Cooking steps")
                        egg.cookingSteps.forEachIndexed { index, step ->
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Text(text = "Step ${index + 1}:")
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = step)
                            }
                        }
                    }
                }
            }
        }
    }

    AnimatedVisibility(visible = showDialog) {
        CustomDialog(
            modifier = Modifier.padding(horizontal = 48.dp),
            title = "Are you sure you want to remove this egg recipe?",
            onDismissRequest = { showDialog = false },
            onConfirm = {
                viewModel.deleteEggFromLocal(egg.id.toString())
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }
}
