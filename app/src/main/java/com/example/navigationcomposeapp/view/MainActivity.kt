package com.example.navigationcomposeapp.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.navigationcomposeapp.model.Screen
import com.example.navigationcomposeapp.ui.theme.NavigationComposeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationComposeAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavigatorUIScreen(navController = rememberNavController())
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NavigationComposeAppTheme {
        NavigatorUIScreen(navController = rememberNavController())
    }
}

@Composable
fun NavigatorUIScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Screen.Players.Page
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Players.Page) {
            PlayersListData { inputData ->
                navController.navigate(Screen.IndividualPlayer.Page + "/$inputData")
            }
        }
        composable(
            Screen.IndividualPlayer.Page + "/{userdata}",
            arguments = listOf(navArgument("userdata") {
                type = NavType.StringType
            })
        ) {
            val resultData = it.arguments?.getString("userdata")
            IndividualPlayesData(resultData!!) {
                navController.navigate(Screen.DummyData.Page + "/$it")
            }
        }
        composable(Screen.DummyData.Page + "/{dummy}") {
            DummyDataScreen(it.arguments?.getString("dummy")!!)
        }
    }
}