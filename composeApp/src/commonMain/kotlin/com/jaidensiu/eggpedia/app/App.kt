package com.jaidensiu.eggpedia.app

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.jaidensiu.eggpedia.ui.home.HomeScreen
import com.jaidensiu.eggpedia.ui.home.HomeViewModel
import com.jaidensiu.eggpedia.ui.list.EggsListScreen
import com.jaidensiu.eggpedia.ui.list.EggsListViewModel
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
                    HomeScreen(
                        viewModel = koinViewModel<HomeViewModel>(),
                        onClickEggsList = { navController.navigate(route = Route.EggsList) },
                        onClickCreateRecipe = { navController.navigate(route = Route.EggsList) }
                    )
                }
                composable<Route.EggsList> {
                    EggsListScreen(
                        viewModel = koinViewModel<EggsListViewModel>(),
                        onClickBack = { navController.navigate(route = Route.Home) }
                    )
                }
            }
        }
    }
}
