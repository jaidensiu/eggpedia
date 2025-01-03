package com.jaidensiu.eggpedia.ui.minigames.speed

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.jaidensiu.eggpedia.ui.shared.CustomDialog
import eggpedia.composeapp.generated.resources.Res
import eggpedia.composeapp.generated.resources.no
import eggpedia.composeapp.generated.resources.yes
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.floor

@Composable
fun SpeedMatchingMinigameScreen(
    viewModel: SpeedMatchingMinigameViewModel = koinViewModel(),
    onDismissGame: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    var showStartDialog by remember { mutableStateOf(value = true) }

    LaunchedEffect(Unit) {
        viewModel.initEggs()
    }

    LaunchedEffect(state.totalTime) {
        if (state.totalTime != null) {
            viewModel.initEggs()
        }
    }

    AnimatedVisibility(
        visible = showStartDialog,
        exit = ExitTransition.None
    ) {
        CustomDialog(
            modifier = Modifier.padding(horizontal = 48.dp),
            title = "Ready to play the speed matching minigame?",
            message = "Select the correct egg image based on the recipe name as fast as you can!",
            confirmText = stringResource(Res.string.yes),
            dismissText = stringResource(Res.string.no),
            onConfirm = {
                showStartDialog = false
                viewModel.onPlay()
            },
            onDismiss = onDismissGame
        )
    }

    AnimatedVisibility(
        visible = !showStartDialog && state.totalTime == null,
        enter = EnterTransition.None,
        exit = ExitTransition.None
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = state.currentEgg ?: "",
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colors.onSurface
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(count = SpeedMatchingMinigameViewModel.GRID_SIZE),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(count = state.shuffledImages.size) { idx ->
                    if (idx + 1 <= SpeedMatchingMinigameViewModel.GRID_ITEMS_SIZE) {
                        val imageUrl = state.shuffledImages[idx]
                        Image(
                            painter = rememberAsyncImagePainter(model = imageUrl),
                            contentDescription = null,
                            modifier = Modifier
                                .aspectRatio(ratio = 1f)
                                .clickable { viewModel.checkImageClicked(imageUrl) },
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
            Text(
                text = state.errorMessage ?: "Select the correct egg image",
                modifier = Modifier.padding(16.dp),
                color = if (state.errorMessage != null) {
                    MaterialTheme.colors.error
                } else {
                    MaterialTheme.colors.onSurface
                }
            )
        }
    }

    AnimatedVisibility(
        visible = state.totalTime != null,
        exit = ExitTransition.None
    ) {
        val totalTimeSeconds = state.totalTime?.div(other = 1000.0) ?: 0.0
        val beforeDecimal = floor(totalTimeSeconds).toInt()
        val afterDecimal = ((totalTimeSeconds - beforeDecimal) * 100).toInt()

        CustomDialog(
            modifier = Modifier.padding(horizontal = 48.dp),
            title = if (viewModel.isBestTime(state.totalTime)) "Good job, a best time!" else "Good job!",
            message = "Time: $beforeDecimal.$afterDecimal seconds\nBest time: ${viewModel.getBestTime()} seconds",
            confirmText = "Play again",
            dismissText = "Back to minigames",
            onConfirm = viewModel::onPlayAgain,
            onDismiss = onDismissGame
        )
    }
}
