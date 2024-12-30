package com.jaidensiu.eggpedia.ui.games

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jaidensiu.eggpedia.ui.shared.isAndroid
import com.plusmobileapps.konnectivity.Konnectivity
import eggpedia.composeapp.generated.resources.Res
import eggpedia.composeapp.generated.resources.back
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EggQuizGamesScreen(
    viewModel: EggQuizGamesViewModel = koinViewModel(),
    onClickBack: () -> Unit,
    playImageMatching: () -> Unit,
    playCookingStepsOrdering: () -> Unit,
    playMixOfQuestions: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val konnectivity = remember { Konnectivity() }
    val isConnected by konnectivity.isConnectedState.collectAsState()

    LaunchedEffect(isConnected) {
        viewModel.observeInternetConnection(isConnected = isConnected)
    }

    LaunchedEffect(Unit) {
        viewModel.getEggs()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(
                start = 32.dp,
                top = if (isAndroid) 32.dp else 54.dp,
                end = 32.dp,
                bottom = 24.dp
            )
    ) {
        TextButton(
            onClick = { onClickBack() },
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.primary)
        ) {
            Text(
                text = stringResource(Res.string.back),
                color = MaterialTheme.colors.primary
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        if (!state.isConnectedToInternet || !isConnected) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Internet connection is needed for the quiz game",
                    color = MaterialTheme.colors.onBackground
                )
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Which quiz game would you like to play?",
                    color = MaterialTheme.colors.onBackground
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = playImageMatching,
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                ) {
                    Text(
                        text = "Image matching",
                        color = MaterialTheme.colors.onPrimary
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = playCookingStepsOrdering,
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                ) {
                    Text(
                        text = "Cooking steps ordering",
                        color = MaterialTheme.colors.onPrimary
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = playMixOfQuestions,
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                ) {
                    Text(
                        text = "Mix of questions",
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
    }
}
