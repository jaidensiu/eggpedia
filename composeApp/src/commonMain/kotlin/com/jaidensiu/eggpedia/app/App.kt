package com.jaidensiu.eggpedia.app

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.jaidensiu.eggpedia.ui.details.EggDetailsScreen
import com.jaidensiu.eggpedia.ui.details.EggDetailsViewModel
import com.jaidensiu.eggpedia.ui.home.HomeScreen
import com.jaidensiu.eggpedia.ui.home.HomeViewModel
import com.jaidensiu.eggpedia.ui.list.EggsListScreen
import com.jaidensiu.eggpedia.ui.list.EggsListViewModel
import com.jaidensiu.eggpedia.ui.minigames.MinigamesScreen
import com.jaidensiu.eggpedia.ui.minigames.MinigamesViewModel
import com.jaidensiu.eggpedia.ui.minigames.memory.MemoryMatchingMinigameScreen
import com.jaidensiu.eggpedia.ui.minigames.speed.SpeedMatchingMinigameScreen
import com.jaidensiu.eggpedia.ui.minigames.speed.SpeedMatchingMinigameViewModel
import com.jaidensiu.eggpedia.ui.shared.SelectedEggViewModel
import com.jaidensiu.eggpedia.ui.theme.EggpediaTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    val navController = rememberNavController()

    EggpediaTheme {
        NavHost(
            navController = navController,
            startDestination = Route.RouteGraph
        ) {
            navigation<Route.RouteGraph>(startDestination = Route.Home) {
                composable<Route.Home> {
                    val viewModel = koinViewModel<HomeViewModel>()

                    HomeScreen(
                        viewModel = viewModel,
                        onExploreEggRecipes = { navController.navigate(route = Route.EggsList) },
                        onViewSavedEggRecipes = { navController.navigate(route = Route.SavedEggsList) },
                        onPlayEggQuizGames = { navController.navigate(route = Route.EggMinigames) }
                    )
                }

                composable<Route.EggsList> { navBackStackEntry ->
                    val viewModel = koinViewModel<EggsListViewModel>()
                    val selectedEggViewModel = navBackStackEntry.sharedKoinViewModel<SelectedEggViewModel>(navController)

                    EggsListScreen(
                        viewModel = viewModel,
                        route = Route.EggsList,
                        onClickBack = { navController.navigate(route = Route.Home) },
                        onSelectEgg = { egg ->
                            selectedEggViewModel.onSelectEgg(egg)
                            navController.navigate(route = Route.EggDetails(egg.id.toString()))
                        }
                    )
                }

                composable<Route.SavedEggsList> { navBackStackEntry ->
                    val viewModel = koinViewModel<EggsListViewModel>()
                    val selectedEggViewModel = navBackStackEntry.sharedKoinViewModel<SelectedEggViewModel>(navController)

                    EggsListScreen(
                        viewModel = viewModel,
                        route = Route.SavedEggsList,
                        onClickBack = { navController.navigate(route = Route.Home) },
                        onSelectEgg = { egg ->
                            selectedEggViewModel.onSelectEgg(egg)
                            navController.navigate(route = Route.EggDetails(egg.id.toString()))
                        }
                    )
                }

                composable<Route.EggDetails> { navBackStackEntry ->
                    val viewModel = koinViewModel<EggDetailsViewModel>()
                    val selectedEggViewModel = navBackStackEntry.sharedKoinViewModel<SelectedEggViewModel>(navController)
                    val selectedEgg = selectedEggViewModel.selectedEgg.collectAsStateWithLifecycle()

                    selectedEgg.value?.let { egg ->
                        EggDetailsScreen(
                            viewModel = viewModel,
                            egg = egg,
                            onClickBack = { navController.navigateUp() }
                        )
                    }
                }

                composable<Route.EggMinigames> {
                    val viewModel = koinViewModel<MinigamesViewModel>()

                    MinigamesScreen(
                        viewModel = viewModel,
                        onClickBack = { navController.navigateUp() },
                        playSpeedMatchingMinigame = { navController.navigate(route = Route.SpeedMatchingMinigame) },
                        playMemoryMatchingMinigame = { navController.navigate(route = Route.MemoryMatchingMinigame) }
                    )
                }

                composable<Route.SpeedMatchingMinigame> {
                    val viewModel = koinViewModel<SpeedMatchingMinigameViewModel>()

                    SpeedMatchingMinigameScreen(
                        viewModel = viewModel,
                        onDismissGame = { navController.navigateUp() }
                    )
                }

                composable<Route.MemoryMatchingMinigame> {
                    MemoryMatchingMinigameScreen()
                }
            }
        }
    }
}
