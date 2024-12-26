package com.jaidensiu.eggpedia.app

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
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
import com.jaidensiu.eggpedia.ui.shared.SelectedEggViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    val navController = rememberNavController()

    MaterialTheme {
        NavHost(navController = navController, startDestination = Route.RouteGraph) {
            navigation<Route.RouteGraph>(startDestination = Route.Home) {
                composable<Route.Home> {
                    val viewModel = koinViewModel<HomeViewModel>()

                    HomeScreen(
                        viewModel = viewModel,
                        onClickEggsList = { navController.navigate(route = Route.EggsList) },
                        onClickSavedEggsList = { navController.navigate(route = Route.SavedEggsList) }
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
            }
        }
    }
}

@Composable
private inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(key1 = this) { navController.getBackStackEntry(navGraphRoute) }

    return koinViewModel(viewModelStoreOwner = parentEntry)
}
