package com.example.navigationcomposeapp.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.navigationcomposeapp.model.Screen
import com.example.navigationcomposeapp.ui.theme.NavigationComposeAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationComposeAppTheme {
                ConstructAppBar()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NavigationComposeAppTheme {
        ConstructAppBar()
    }
}

@Composable
fun ConstructAppBar() {
    val scafoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scafoldState,
    ) {
        DesignDrawer()
    }
}

@Composable
fun DesignDrawer() {
    val navController = rememberNavController()
    val draweState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val openDrawer = {
        scope.launch {
            draweState.open()
        }
    }
    Surface(modifier = Modifier.fillMaxSize()) {
        ModalDrawer(
            drawerState = draweState,
            gesturesEnabled = draweState.isOpen,
            drawerContent = {
                Drawer { route ->
                    scope.launch {
                        draweState.close()
                    }
                    navController.navigate(route) {
                        popUpTo = navController.graph.startDestinationId
                        launchSingleTop = true
                    }
                }
            }
        ) {
            NavigatorUIScreen(
                openDrawer = { openDrawer() },
                navController = navController
            )
        }
    }
}

@Composable
fun NavigatorUIScreen(
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Screen.Players.Page
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        playersData(navController, openDrawer)
        profileSetUp(navController = navController)
        aboutApp(navController = navController)
    }
}