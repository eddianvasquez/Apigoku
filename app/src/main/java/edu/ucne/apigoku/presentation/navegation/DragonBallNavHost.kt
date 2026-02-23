package edu.ucne.apigoku.presentation.navegation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.apigoku.presentation.api.detail.PlanetDetailScreen
import edu.ucne.apigoku.presentation.api.list.PlanetListScreen

@Composable
fun DragonBallNavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.PlanetList
    ) {
        composable<Screen.PlanetList> {
            PlanetListScreen(
                onPlanetClick = { id ->
                    navHostController.navigate(Screen.PlanetDetail(id))
                }
            )
        }

        composable<Screen.PlanetDetail> {
            PlanetDetailScreen(
                onBack = {
                    navHostController.navigateUp()
                }
            )
        }
    }
}