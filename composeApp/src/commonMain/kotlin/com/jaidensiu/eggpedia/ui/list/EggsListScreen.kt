package com.jaidensiu.eggpedia.ui.list

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EggsListScreen(
    viewModel: EggsListViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val state = viewModel.state.collectAsState()

    Column(
        modifier = modifier
    ) {
        state.value.eggs.forEach {
            Text(
                text = it.name
            )
        }
    }
}
