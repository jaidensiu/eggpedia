package com.jaidensiu.eggpedia.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import eggpedia.composeapp.generated.resources.Res
import eggpedia.composeapp.generated.resources.egg_icon
import eggpedia.composeapp.generated.resources.explore_egg_recipes
import eggpedia.composeapp.generated.resources.good_time_of_day
import eggpedia.composeapp.generated.resources.play_minigames
import eggpedia.composeapp.generated.resources.view_saved_egg_recipes
import eggpedia.composeapp.generated.resources.welcome_to_eggpedia
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
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
        Image(
            painter = painterResource(Res.drawable.egg_icon),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(fraction = 0.5f)
        )
        Text(
            text = stringResource(Res.string.good_time_of_day, state.value.timeOfDay),
            color = MaterialTheme.colors.onBackground
        )
        Text(
            text = stringResource(Res.string.welcome_to_eggpedia),
            color = MaterialTheme.colors.onBackground
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { onExploreEggRecipes() },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
        ) {
            Text(
                text = stringResource(Res.string.explore_egg_recipes),
                color = MaterialTheme.colors.onPrimary
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = { onViewSavedEggRecipes() },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
        ) {
            Text(
                text = stringResource(Res.string.view_saved_egg_recipes),
                color = MaterialTheme.colors.onPrimary
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = { onPlayEggQuizGames() },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
        ) {
            Text(
                text = stringResource(Res.string.play_minigames),
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}
