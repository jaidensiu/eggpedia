package com.jaidensiu.eggpedia.ui.minigames.memory

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.jaidensiu.eggpedia.ui.shared.CustomDialog
import eggpedia.composeapp.generated.resources.Res
import eggpedia.composeapp.generated.resources.no
import eggpedia.composeapp.generated.resources.yes
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MemoryMatchingMinigameScreen(
    viewModel: MemoryMatchingMinigameViewModel = koinViewModel(),
    onDismissGame: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    var showStartDialog by remember { mutableStateOf(value = true) }
    var showSelectLevelDialog by remember { mutableStateOf(value = false) }

    LaunchedEffect(Unit) {
        viewModel.initEggs()
    }

    AnimatedVisibility(visible = showStartDialog) {
        CustomDialog(
            modifier = Modifier.padding(horizontal = 48.dp),
            title = "Ready to play the memory matching minigame?",
            message = "Select pairs of hidden images that matches that match each other!",
            confirmText = stringResource(Res.string.yes),
            dismissText = stringResource(Res.string.no),
            onConfirm = {
                showStartDialog = false
                showSelectLevelDialog = true
            },
            onDismiss = onDismissGame
        )
    }

    AnimatedVisibility(visible = showSelectLevelDialog) {
        CustomDialog(
            modifier = Modifier.padding(48.dp),
            title = "Select difficulty",
            onConfirmEnabled = state.difficulty != null,
            confirmText = "Start",
            dismissText = "Back",
            onConfirm = {
                viewModel.onPlay()
                showSelectLevelDialog = false
            },
            onDismiss = {
                viewModel.dismissDifficulty()
                showStartDialog = true
                showSelectLevelDialog = false
            }
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { viewModel.onSelectDifficulty(MemoryMatchingMinigameDifficulty.EASY) },
                    modifier = Modifier.fillMaxWidth(fraction = 0.75f),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (state.difficulty == MemoryMatchingMinigameDifficulty.EASY) {
                            MaterialTheme.colors.secondary
                        } else {
                            MaterialTheme.colors.primary
                        }
                    )
                ) {
                    Text(text = "Easy")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { viewModel.onSelectDifficulty(MemoryMatchingMinigameDifficulty.MEDIUM) },
                    modifier = Modifier.fillMaxWidth(fraction = 0.75f),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (state.difficulty == MemoryMatchingMinigameDifficulty.MEDIUM) {
                            MaterialTheme.colors.secondary
                        } else {
                            MaterialTheme.colors.primary
                        }
                    )
                ) {
                    Text(text = "Medium")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { viewModel.onSelectDifficulty(MemoryMatchingMinigameDifficulty.HARD) },
                    modifier = Modifier.fillMaxWidth(fraction = 0.75f),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (state.difficulty == MemoryMatchingMinigameDifficulty.HARD) {
                            MaterialTheme.colors.secondary
                        } else {
                            MaterialTheme.colors.primary
                        }
                    )
                ) {
                    Text(text = "Hard")
                }
            }
        }
    }

    AnimatedVisibility(visible = !showStartDialog && !showSelectLevelDialog) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(count = MemoryMatchingMinigameViewModel.GRID_COLUMNS),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val gridItemsSize = viewModel.getGridItemsSize()

                items(count = state.randomEggImages.size) { idx ->
                    if (idx < gridItemsSize) {
                        val imageUrl = state.randomEggImages[idx]
                        val isFlipped = state.flippedCards.contains(idx) || state.matchedCards.contains(idx)
                        val numFlipped = state.flippedCards.size

                        Image(
                            painter = rememberAsyncImagePainter(model = if (isFlipped) imageUrl else null),
                            contentDescription = null,
                            modifier = Modifier
                                .aspectRatio(ratio = 1f)
                                .clickable(enabled = !isFlipped && numFlipped != 2) {
                                    viewModel.checkImageClicked(idx)
                                }
                                .border(
                                    width = 1.dp,
                                    color = if (isFlipped) Color.Transparent else MaterialTheme.colors.onSurface
                                ),
                            contentScale = ContentScale.Crop,
                        )
                    }
                }
            }
        }
    }
}
