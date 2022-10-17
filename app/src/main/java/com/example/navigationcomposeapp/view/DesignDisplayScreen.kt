package com.example.navigationcomposeapp.view

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.navigationcomposeapp.CachedPlayerDetails
import com.example.navigationcomposeapp.model.PlayerDetails
import com.example.navigationcomposeapp.model.TennisPlayersState
import com.example.navigationcomposeapp.viewmodel.PlayerListViewModel

@Composable
fun PlayersListItem(
    content: PlayerDetails,
    onNavigateToPlayersData: (data: String) -> Unit,
    iconTransformationBuilder: ImageRequest.Builder.() -> Unit = {}
) {
    Card(shape = RoundedCornerShape(20.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
            .clickable {
                onNavigateToPlayersData(content.name)
                CachedPlayerDetails.setPlayersDetailsInCache(content)
            }) {
        Row(modifier = Modifier.animateContentSize()) {
            Box(modifier = Modifier.align(alignment = Alignment.CenterVertically)) {
                LoadImagesRes(content.imgURL, iconTransformationBuilder)
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = content.name)
                Text(text = content.country)
            }
        }
    }
}

@Composable
fun LoadImagesRes(imgUrl: String, iconTransformationBuilder: ImageRequest.Builder.() -> Unit) {
    Image(
        painter = rememberImagePainter(
            data = imgUrl,
            builder = iconTransformationBuilder
        ),
        modifier = Modifier
            .size(90.dp)
            .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
            .clip(RoundedCornerShape(10.dp)),
        contentDescription = "image loading"
    )
}

@Composable
fun PlayersListData(
    onNavigateToPlayersData: (data: String) -> Unit
) {
    val viewModel: PlayerListViewModel = hiltViewModel()
    val uiState = viewModel.playerData.collectAsState().value
    when (uiState) {
        is TennisPlayersState.LOADING -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(modifier = Modifier.size(100.dp))
            }
        }
        is TennisPlayersState.SUCCESS -> {
            Surface(modifier = Modifier.fillMaxSize()) {
                LazyColumn() {
                    itemsIndexed(items = uiState.playersList) { index, item ->
                        PlayersListItem(item, onNavigateToPlayersData)
                    }
                }
            }
        }
        is TennisPlayersState.ERROR -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Something went wrong :${uiState.errorMsg}")
            }
        }
    }

}

@Composable
fun IndividualPlayesData(outputData: String, onClickedTxt: (inpiut: String) -> Unit) {
    val content = CachedPlayerDetails.getPlayersDetailsInCache()
    Card(
        modifier = Modifier
            .wrapContentSize()
            .padding(
                start = 16.dp,
                top = 16.dp,
                bottom = 16.dp,
                end = 16.dp
            ),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = outputData)
            PlacePlayerImage(content.imgURL)

            Text(
                text = content.country,
                modifier = Modifier.clickable { onClickedTxt(content.name) }
            )
            Text(text = content.city)
        }
    }
}

@Composable
fun PlacePlayerImage(playerImg: String, IconTransform: ImageRequest.Builder.() -> Unit = {}) {
    Box(contentAlignment = Alignment.Center) {
        Image(
            painter = rememberImagePainter(
                data = playerImg,
                builder = IconTransform
            ),
            modifier = Modifier
                .size(300.dp)
                .padding(top = 20.dp, bottom = 20.dp),
            contentDescription = "loading Player Image"
        )
    }
}

@Composable
fun DummyDataScreen(outputData: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Year : $outputData"
        )
    }
}