package com.example.navigationcomposeapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = 2.dp,
            backgroundColor = MaterialTheme.colors.primary,
        ) {
            Text(
                text = "Categories",
                textAlign = TextAlign.Start,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ), modifier = Modifier
                    .padding(all = 16.dp)
            )
        }
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 20.dp)
        ) {
            Screens.forEach { screen ->
                Spacer(modifier = Modifier.height(20.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    elevation = 2.dp,
                    backgroundColor = MaterialTheme.colors.secondaryVariant,
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = screen,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .padding(all = 16.dp)
                            .clickable {
                                onNavigationFromDrwer(screen)
                            })
                }

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