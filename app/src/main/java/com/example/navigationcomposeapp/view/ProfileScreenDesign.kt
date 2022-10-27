package com.example.navigationcomposeapp.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.navigationcomposeapp.model.PlayerDetails
import com.example.navigationcomposeapp.model.TennisPlayersState
import com.example.navigationcomposeapp.viewmodel.PlayerListViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProfileScreenDesign(onClickedData: (String) -> Unit) {
    val pagerState = rememberPagerState()
    val profileViewModel: PlayerListViewModel = hiltViewModel()
    val profileUiState = profileViewModel.playerData.collectAsState().value
    when (profileUiState) {
        is TennisPlayersState.LOADING -> {
            LoadingIndicator()
        }
        is TennisPlayersState.SUCCESS -> {
            HorizontalPagerDesign(pagerState, profileUiState.playersList, onClickedData)
        }
        is TennisPlayersState.ERROR -> {
            ShowingErrorCode(errorMsg = profileUiState.errorMsg)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HorizontalPagerDesign(
    pagerState: PagerState,
    profileList: List<PlayerDetails>,
    onClickedData: (String) -> Unit
) {
    HorizontalPager(count = profileList.size, state = pagerState) { page ->
        CardDesign(profileList.get(page), onClickedData)
    }
    Spacer(modifier = Modifier.height(10.dp))
    DotIndication(pagerState.currentPage, profileList.size)
}

@Composable
fun CardDesign(
    playerDetails: PlayerDetails,
    onClickedData: (String) -> Unit,
    IconBuilder: ImageRequest.Builder.() -> Unit = {},
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                onClickedData(playerDetails.name)
            }
            .padding(all = 20.dp),
        elevation = 2.dp,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(3.dp, Color.Gray)
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = playerDetails.name,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 20.dp)
            )
            Image(
                painter = rememberImagePainter(
                    data = playerDetails.imgURL,
                    builder = IconBuilder
                ),
                contentDescription = "load profileImage",
                modifier = Modifier
                    .wrapContentWidth()
                    .height(300.dp)
            )
            Text(
                text = playerDetails.country,
                style = TextStyle(fontSize = 14.sp),
                modifier = Modifier.padding(bottom = 20.dp)
            )
        }
    }
}

@Composable
fun DotIndication(selectedPosition: Int, totalCount: Int) {
    LazyRow(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ) {
        items(totalCount) { index ->
            if (selectedPosition == index) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(Color.Red)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                )
            }
            if (index != totalCount - 1) {
                Spacer(modifier = Modifier.padding(3.dp))
            }
        }
    }
}