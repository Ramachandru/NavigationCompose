package com.example.navigationcomposeapp.view

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.navigationcomposeapp.model.MainRoute
import com.example.navigationcomposeapp.model.NavigationKey
import com.example.navigationcomposeapp.model.OtherScreen
import com.example.navigationcomposeapp.model.Screen

fun NavGraphBuilder.playersData(navController: NavController, openDrawer: () -> Unit) {
    composable(Screen.Players.Page) {
        PlayersListDataWithAppBar(openDrawer) { inputData ->
            navController.navigate(Screen.IndividualPlayer.Page + "/$inputData")
        }
    }
    composable(
        Screen.IndividualPlayer.Page + NavigationKey.PLAYER_DATA,
        arguments = listOf(navArgument(NavigationKey.PLAYER_DATA_ARGUMENT) {
            type = NavType.StringType
        })
    ) {
        // val resultData = it.arguments?.getString(NavigationKey.PLAYER_DATA_ARGUMENT)
        IndividualPlayerWithAppBar(navController) {
            navController.navigate(Screen.DummyData.Page + "/$it")
        }
    }
    composable(
        Screen.DummyData.Page + NavigationKey.DUMMY_DATA,
        arguments = listOf(navArgument(NavigationKey.DUMMY_DATA_ARGUMENT) {
            type = NavType.StringType
        })
    ) {
        DummyDataWithAppBar(
            navController,
            it.arguments?.getString(NavigationKey.DUMMY_DATA_ARGUMENT)!!
        )
    }
}

fun NavGraphBuilder.profileSetUp(navController: NavController) {
    navigation(
        route = MainRoute.ProfileScreen.route,
        startDestination = OtherScreen.Profile.Page
    ) {
        composable(OtherScreen.Profile.Page) {
            ProfileScreen(navController = navController)
        }
    }
}

fun NavGraphBuilder.aboutApp(navController: NavController) {
    navigation(
        route = MainRoute.AboutScreen.route,
        startDestination = OtherScreen.About.Page
    ) {
        composable(OtherScreen.About.Page) {
            AboutScreen(navController = navController)
        }
    }
}