package com.jaidensiu.eggpedia

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jaidensiu.eggpedia.presentation.list.EggsListScreen
import com.jaidensiu.eggpedia.presentation.list.EggsListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    val viewModel = koinViewModel<EggsListViewModel>()

    MaterialTheme {
        EggsListScreen(
            viewModel = viewModel,
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
        )
    }
}
