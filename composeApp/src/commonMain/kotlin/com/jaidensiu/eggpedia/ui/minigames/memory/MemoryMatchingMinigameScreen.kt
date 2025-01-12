package com.jaidensiu.eggpedia.ui.minigames.memory

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.floor

@Composable
fun MemoryMatchingMinigameScreen(
    viewModel: MemoryMatchingMinigameViewModel = koinViewModel(),
    onDismissGame: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    var showStartDialog by remember { mutableStateOf(value = true) }
    val coroutineScope = rememberCoroutineScope()

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
            title = "Ready to play the memory matching minigame?",
            message = "Find and match the pairs of hidden images as fast as you can!",
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
                text = "Find all the pairs of eggs",
                color = MaterialTheme.colors.onSurface
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(count = MemoryMatchingMinigameViewModel.GRID_COLUMNS),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(count = state.randomEggImages.size) { idx ->
                    if (idx < MemoryMatchingMinigameViewModel.TOTAL_CARDS) {
                        val imageUrl = state.randomEggImages[idx]
                        val isFlipped =
                            state.flippedCards.contains(idx) || state.matchedCards.contains(idx)
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

    AnimatedVisibility(
        visible = state.matchedCards.size == MemoryMatchingMinigameViewModel.TOTAL_CARDS && state.totalTime != null,
        exit = ExitTransition.None
    ) {
        val totalTimeSeconds = state.totalTime?.div(other = 1000.0) ?: Double.MAX_VALUE
        val beforeDecimal = floor(totalTimeSeconds).toInt()
        val afterDecimal = ((totalTimeSeconds - beforeDecimal) * 1000).toInt()
        val timeInfo = "Time: $beforeDecimal.$afterDecimal seconds"
        var bestTimeInfo by remember { mutableStateOf(value = "Best time: $beforeDecimal.$afterDecimal seconds") }

        LaunchedEffect(Unit) {
            viewModel.saveTime(time = state.totalTime)
            val bestTimeSeconds = viewModel.getBestTime()?.div(other = 1000.0) ?: Double.MAX_VALUE
            val before = floor(bestTimeSeconds).toInt()
            val after = ((bestTimeSeconds - before) * 1000).toInt()
            bestTimeInfo = "Best time: $before.$after seconds"
        }

        CustomDialog(
            modifier = Modifier.padding(horizontal = 48.dp),
            title = "Would you like to play again?",
            message = "$timeInfo\n$bestTimeInfo",
            confirmText = "Yes",
            dismissText = "No",
            onConfirm = {
                coroutineScope.launch {
                    viewModel.onResetMinigameState()
                    viewModel.initEggs()
                    viewModel.onPlay()
                }
            },
            onDismiss = onDismissGame
        )
    }
}
