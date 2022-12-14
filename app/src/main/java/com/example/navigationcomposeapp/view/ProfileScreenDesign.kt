package com.example.navigationcomposeapp.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.navigationcomposeapp.CachedPlayerDetails
import com.example.navigationcomposeapp.model.PlayerDetails
import com.example.navigationcomposeapp.model.TennisPlayersState
import com.example.navigationcomposeapp.viewmodel.PlayerListViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProfileScreenDesign(position: Int, onClickedData: (String) -> Unit) {
    val pagerState = rememberPagerState()
    val profileViewModel: PlayerListViewModel = hiltViewModel()
    val profileUiState = profileViewModel.playerData.collectAsState().value
    when (profileUiState) {
        is TennisPlayersState.LOADING -> {
            LoadingIndicator()
        }
        is TennisPlayersState.SUCCESS -> {
            ModularizeProfileAndGender(
                position,
                pagerState,
                profileUiState.playersList,
                onClickedData
            )
        }
        is TennisPlayersState.ERROR -> {
            ShowingErrorCode(errorMsg = profileUiState.errorMsg)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ModularizeProfileAndGender(
    position: Int,
    pagerState: PagerState,
    profileList: List<PlayerDetails>,
    onClickedData: (String) -> Unit
) {
    if (position == 2) {
        DesignTabHost(profileList, onClickedData)
        return
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        HorizontalPagerDesign(pagerState, profileList, onClickedData)
        DesignTabHost(profileList, onClickedData)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HorizontalPagerDesign(
    pagerState: PagerState,
    profileList: List<PlayerDetails>,
    onClickedData: (String) -> Unit
) {
    val scope = rememberCoroutineScope()
    val totCount: Int = 50;
    var screenIndex: Int = 0;
    HorizontalPager(
        count = totCount, state = pagerState,
        itemSpacing = -60.dp
    ) { page ->
        screenIndex = page
        if (screenIndex >= profileList.size) {
            screenIndex %= profileList.size
        }
        val player = profileList.get(screenIndex)
        CardDesign(player, onClickedData)
    }
    Spacer(modifier = Modifier.height(10.dp))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DotIndication(profileList.size, pagerState, scope)
    }
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
                CachedPlayerDetails.setPlayersDetailsInCache(playerDetails)
                onClickedData(playerDetails.name)
            }
            .padding(all = 40.dp),
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

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DotIndication(
    totalCount: Int,
    pagerState: PagerState,
    scope: CoroutineScope
) {
    val selectedPosition = pagerState.currentPage
    var indicatorIndex: Int = selectedPosition

    LazyRow(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ) {
        items(totalCount) { index ->
            if (indicatorIndex >= totalCount) {
                indicatorIndex %= totalCount
            }
            if (indicatorIndex == index) {
                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .height(12.dp)
                        .clip(shape = CircleShape)
                        .background(Color.Red)
                        .clickable {
                            //nothing implemented
                        }
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                        .clickable {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                )
            }
            if (index != totalCount - 1) {
                Spacer(modifier = Modifier.padding(3.dp))
            }
        }
    }
}

@Composable
fun ProfileSubCardDesign() {
    val playerDetails: PlayerDetails = CachedPlayerDetails.getPlayersDetailsInCache()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 10.dp)
    ) {
        Image(
            painter = rememberImagePainter(playerDetails.imgURL),
            contentDescription = "load",
            modifier = Modifier
                .width(400.dp)
                .height(200.dp), contentScale = ContentScale.FillBounds
        )
        Text(
            text = playerDetails.name,
            style = TextStyle(
                fontSize = 18.sp, fontWeight = FontWeight.ExtraBold,
                color = Color.Black, background = Color.White
            ),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(all = 8.dp)
        )
        Text(
            text = playerDetails.country,
            style = TextStyle(
                fontSize = 18.sp, fontWeight = FontWeight.Bold,
                color = Color.Black, background = Color.White
            ),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(all = 8.dp)
        )
    }
}
