package com.example.navigationcomposeapp.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.navigationcomposeapp.model.AboutRoute
import com.example.navigationcomposeapp.model.ProfileRoute
import com.example.navigationcomposeapp.model.Screen

val Screens = listOf<String>(
    Screen.Players.Page,
    ProfileRoute.Profile.page,
    AboutRoute.About.Page
)

@Composable
fun Drawer(onNavigationFromDrwer: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .wrapContentSize()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = 2.dp,
                backgroundColor = Color.LightGray
            ) {
                Text(text = "")
            }
            Screens.forEach { screen ->
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = screen,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .padding(all = 8.dp)
                        .clickable {
                            onNavigationFromDrwer(screen)
                        })
            }
        }
    }
}

@Composable
fun CreateAppBar(title: String, iconImage: ImageVector, onBtnClicked: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onBtnClicked) {
                Icon(
                    imageVector = iconImage,
                    modifier = Modifier.padding(horizontal = 12.dp),
                    contentDescription = "Action Home"
                )
            }
        },
        title = { Text(title) },
        backgroundColor = MaterialTheme.colors.primary
    )
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.size(100.dp), color = Color.Green)
    }
}

@Composable
fun ShowingErrorCode(errorMsg: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Something went wrong :${errorMsg}")
    }
}