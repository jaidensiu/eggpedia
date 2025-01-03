package com.jaidensiu.eggpedia.ui.games.image

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.jaidensiu.eggpedia.ui.shared.CustomDialog
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ImageMatchingGameScreen(
    viewModel: ImageMatchingGameViewModel = koinViewModel(),
    onDismissGame: () -> Unit,
    onPlay: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    var showStartDialog by remember { mutableStateOf(value = true) }
    var showEndDialog by remember { mutableStateOf(value = false) }

    LaunchedEffect(Unit) {
        viewModel.initEggNamesAndImages()
    }

    AnimatedVisibility(visible = showStartDialog) {
        CustomDialog(
            modifier = Modifier.padding(horizontal = 48.dp),
            title = "Ready to play image matching?",
            message = "Match the egg images with their recipe names as fast as you can!",
            dismissText = "No",
            confirmText = "Yes",
            onConfirm = {
                showStartDialog = false
                onPlay()
            },
            onDismiss = {
                onDismissGame()
            }
        )
    }

    if (!showStartDialog) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = state.currentEgg ?: "Game over",
                modifier = Modifier.padding(32.dp),
                color = MaterialTheme.colors.onSurface
            )
            state.errorMessage?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            LazyVerticalGrid(
                columns = GridCells.Fixed(count = ImageMatchingGameViewModel.GRID_SIZE),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(count = state.shuffledImages.size) { idx ->
                    if (idx + 1 <= ImageMatchingGameViewModel.GRID_ITEMS_SIZE) {
                        val imageUrl = state.shuffledImages[idx]
                        Image(
                            painter = rememberAsyncImagePainter(model = imageUrl),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .aspectRatio(ratio = 1f)
                                .clickable { viewModel.checkImageClicked(imageUrl) }
                        )
                    }
                }
            }
        }
    }

    if (state.currentEgg == null && state.totalTime != null) {
        showEndDialog = true
    }

    AnimatedVisibility(visible = showEndDialog) {
        CustomDialog(
            modifier = Modifier.padding(horizontal = 48.dp),
            title = "Good job!",
            message = "Total time: ${state.totalTime?.div(other = 1000)} seconds",
            dismissText = "",
            confirmText = "OK",
            onConfirm = {
                showEndDialog = false
                onDismissGame()
            },
            onDismiss = {}
        )
    }
}
