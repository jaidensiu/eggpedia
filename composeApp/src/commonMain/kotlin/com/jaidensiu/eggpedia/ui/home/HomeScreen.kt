package com.jaidensiu.eggpedia.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onClickEggsList: () -> Unit,
    onClickSavedEggsList: () -> Unit
) {
    val state = viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Good ${state.value.timeOfDay}")
        Text(text = "Welcome to Eggpedia")
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { onClickEggsList() }) {
            Text(text = "Explore list of egg recipes")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = { onClickSavedEggsList() }) {
            Text(text = "View my saved egg recipes")
        }
    }
}
