package com.jaidensiu.eggpedia

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.jaidensiu.eggpedia.presentation.EggsListScreen
import com.jaidensiu.eggpedia.presentation.EggsViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    val viewModel = koinViewModel<EggsViewModel>()

    MaterialTheme {
        EggsListScreen(
            viewModel = viewModel
        )
    }
}
