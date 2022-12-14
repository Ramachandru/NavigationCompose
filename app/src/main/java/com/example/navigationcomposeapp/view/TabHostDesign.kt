package com.example.navigationcomposeapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.navigationcomposeapp.model.GenderPlayer
import com.example.navigationcomposeapp.model.PlayerDetails
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DesignTabHost(
    profileList: List<PlayerDetails>,
    onClickedData: (String) -> Unit
) {
    val pager = rememberPagerState()
    val genderList = listOf(GenderPlayer.GentsPlayer, GenderPlayer.LadiesPlayer)
    Column(modifier = Modifier.height(500.dp)) {
        DesignTabs(pagerState = pager, tabs = genderList)
        TabContent(
            pagerState = pager, tabs = genderList,
            profileList,
            onClickedData
        )
    }
}

@Composable
fun LoadingTabsContent(gentsList: List<PlayerDetails>, onClickedData: (String) -> Unit) {
    LazyColumn {
        itemsIndexed(gentsList) { index, item ->
            PlayersListItem(content = item, onClickedData)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DesignTabs(pagerState: PagerState, tabs: List<GenderPlayer>) {
    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White,
        indicator = {
            TabRowDefaults.Indicator(Modifier.pagerTabIndicatorOffset(pagerState, it))
        }
    ) {
        tabs.forEachIndexed { index, genderPlayer ->
            Tab(selected = pagerState.currentPage == index, onClick = {
                scope.launch {
                    pagerState.animateScrollToPage(index)
                }
            }, text = { Text(text = genderPlayer.gender) })
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabContent(
    pagerState: PagerState,
    tabs: List<GenderPlayer>,
    genderList: List<PlayerDetails>,
    onClickedData: (String) -> Unit
) {
    val gentsList = genderList.subList(0, (genderList.size + 1) / 2)
    val ladiesList = genderList.subList((genderList.size + 1) / 2, genderList.size)
    HorizontalPager(count = tabs.size, state = pagerState) { page ->
        when (page) {
            0 -> {
                LoadingTabsContent(gentsList, onClickedData)
            }
            1 -> {
                LoadingTabsContent(ladiesList, onClickedData)
            }
        }
    }
}