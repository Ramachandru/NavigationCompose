package com.example.navigationcomposeapp.view

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.navigationcomposeapp.model.*

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
        startDestination = ProfileRoute.Profile.page
    ) {
        composable(ProfileRoute.Profile.page) {
            ProfileScreen(navController = navController) {
                navController.navigate(ProfileRoute.ProfileSub.page + "/$it")
            }
        }
        composable(
            ProfileRoute.ProfileSub.page + NavigationKey.PROFILE_DATA,
            arguments = listOf(navArgument(NavigationKey.PROFILE_DATA_ARGUMENT) {
                type = NavType.StringType
            })
        ) {
            ProfileSubScreen(
                navController,
                it.arguments!!.getString(NavigationKey.PROFILE_DATA_ARGUMENT)!!
            )
        }
    }
}

fun NavGraphBuilder.aboutApp(navController: NavController) {
    navigation(
        route = MainRoute.AboutScreen.route,
        startDestination = AboutRoute.About.Page
    ) {
        composable(AboutRoute.About.Page) {
            AboutScreen(navController = navController) {
                navController.navigate(Screen.IndividualPlayer.Page + "/$it")
            }
        }
    }
}