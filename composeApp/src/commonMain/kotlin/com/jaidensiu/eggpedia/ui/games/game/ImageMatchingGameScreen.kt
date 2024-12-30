package com.jaidensiu.eggpedia.ui.games.game

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jaidensiu.eggpedia.ui.shared.CustomDialog

@Composable
fun ImageMatchingGameScreen(
    onDismissGame: () -> Unit,
    onPlay: () -> Unit
) {
    var showDialog by remember { mutableStateOf(value = true) }

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
                showDialog = false
                onDismissGame()
            }
        )
    }

    // TODO: should be egg recipe name at top with 4 egg images like the Kahoot layout
}
