package com.jaidensiu.eggpedia.ui.games.image

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.jaidensiu.eggpedia.ui.shared.CustomDialog
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ImageMatchingGameScreen(
    viewModel: ImageMatchingGameViewModel = koinViewModel(),
    onDismissGame: () -> Unit,
    onPlay: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    var showDialog by remember { mutableStateOf(value = true) }

    LaunchedEffect(Unit) {
        viewModel.initEggNamesAndImages()
    }

    AnimatedVisibility(visible = showDialog) {
        CustomDialog(
            modifier = Modifier.padding(horizontal = 48.dp),
            title = "Ready to play image matching?",
            message = "Match the egg images with their recipe names as fast as you can!",
            dismissText = "No",
            confirmText = "Yes",
            onConfirm = {
                showDialog = false
                onPlay()
            },
            onDismiss = {
                onDismissGame()
            }
        )
    }

    if (!showDialog) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(state.eggNames.zip(state.eggImages)) { (name, image) ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = name)
                    Text(text = image)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

    // TODO: should be egg recipe name at top with 4 egg images like the Kahoot layout
}
