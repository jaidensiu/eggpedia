package com.jaidensiu.eggpedia.ui.minigames.memory

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

    AnimatedVisibility(
        visible = showStartDialog,
        exit = ExitTransition.None
    ) {
        CustomDialog(
            modifier = Modifier.padding(horizontal = 48.dp),
            title = "Ready to play the memory matching minigame?",
            message = "Select the correct hidden image that matches the egg image!",
            confirmText = stringResource(Res.string.yes),
            dismissText = stringResource(Res.string.no),
            onConfirm = {
                showStartDialog = false
                viewModel.onPlay()
            },
            onDismiss = onDismissGame
        )
    }
}
