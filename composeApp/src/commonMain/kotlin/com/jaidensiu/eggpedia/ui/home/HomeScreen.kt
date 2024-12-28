package com.jaidensiu.eggpedia.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
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
    onExploreEggRecipes: () -> Unit,
    onViewSavedEggRecipes: () -> Unit,
    onPlayEggQuizGames: () -> Unit
) {
    val state = viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Good ${state.value.timeOfDay}",
            color = MaterialTheme.colors.onBackground
        )
        Text(
            text = "Welcome to Eggpedia",
            color = MaterialTheme.colors.onBackground
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { onExploreEggRecipes() },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
        ) {
            Text(
                text = "Explore egg recipes",
                color = MaterialTheme.colors.onPrimary
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = { onViewSavedEggRecipes() },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
        ) {
            Text(
                text = "View saved egg recipes",
                color = MaterialTheme.colors.onPrimary
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = { onPlayEggQuizGames() },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
        ) {
            Text(
                text = "Play egg quiz games",
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}
