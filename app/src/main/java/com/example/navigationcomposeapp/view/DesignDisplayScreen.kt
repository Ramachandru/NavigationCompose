package com.example.navigationcomposeapp.view

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.navigationcomposeapp.CachedPlayerDetails
import com.example.navigationcomposeapp.model.PlayerDetails
import com.example.navigationcomposeapp.model.TennisPlayersState
import com.example.navigationcomposeapp.viewmodel.PlayerListViewModel
import java.util.*

@Composable
fun PlayersListItem(
    content: PlayerDetails,
    onNavigateToPlayersData: (data: String) -> Unit = {},
    iconTransformationBuilder: ImageRequest.Builder.() -> Unit = {}
) {
    Card(shape = RoundedCornerShape(20.dp),
        backgroundColor = MaterialTheme.colors.secondaryVariant,
        border = BorderStroke(
            width = 2.dp,
            color = Color.Gray
        ),
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
            DesignTextWithFormat(
                Triple(content.name, content.country, content.city),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun DesignTextWithFormat(
    tribleData: Triple<String, String, String>,
    modifier: Modifier,
    onClickedTxt: (String) -> Unit = {}
) {
    Column(
        modifier = modifier.clickable {
            onClickedTxt(tribleData.third)
        },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = tribleData.first,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold
            )
        )
        Text(
            text = tribleData.second,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.LightGray,
            ), modifier = Modifier.padding(top = 10.dp)
        )
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
            .clip(RoundedCornerShape(50.dp)),
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
            CustomProgressIndicator()
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
            ShowingErrorCode(errorMsg = uiState.errorMsg)
        }
    }
}

@Composable
fun PlayersListDataWithAppBar(
    openDrawer: () -> Unit,
    onNavigateToPlayersData: (data: String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        CreateAppBar(title = "Player List", iconImage = Icons.Filled.Menu) {
            openDrawer()
        }
        PlayersListData(onNavigateToPlayersData = onNavigateToPlayersData)
    }
}

@Composable
fun IndividualPlayerWithAppBar(
    navController: NavController,
    onClickedTxt: (inpiut: String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        CreateAppBar(title = "Player Details", iconImage = Icons.Filled.ArrowBack) {
            navController.popBackStack()
        }
        IndividualPlayesData(onClickedTxt = onClickedTxt)
    }
}

@Composable
fun DummyDataWithAppBar(navController: NavController, outputData: String) {
    Column(modifier = Modifier.fillMaxSize()) {
        CreateAppBar(title = "Dummy Data", iconImage = Icons.Filled.ArrowBack) {
            navController.popBackStack()
        }
    }
    DummyDataScreen(outputData)
}

@Composable
fun IndividualPlayesData(onClickedTxt: (inpiut: String) -> Unit) {
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
        border = BorderStroke(
            width = 2.dp,
            color = Color.Black
        ),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = MaterialTheme.colors.secondaryVariant
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = content.name, style = TextStyle())
            PlacePlayerImage(content.imgURL)
            DesignTextWithFormat(
                Triple(content.country, content.city, content.name),
                modifier = Modifier
                    .wrapContentSize()
                    .padding(10.dp),
                onClickedTxt
            )
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
                .padding(top = 20.dp, bottom = 20.dp)
                .clip(CircleShape),
            contentDescription = "loading Player Image"
        )
    }
}

@Composable
fun ProfileScreen(navController: NavController, onClickNavigate: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        CreateAppBar(title = "Profile", iconImage = Icons.Filled.ArrowBack) {
            navController.popBackStack()
        }
        ProfileScreenDesign(1, onClickNavigate)
    }
}

@Composable
fun ProfileSubScreen(navController: NavController, data: String) {
    Column(modifier = Modifier.fillMaxSize()) {
        CreateAppBar(title = "Profile-Sub", iconImage = Icons.Filled.ArrowBack) {
            navController.popBackStack()
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileSubCardDesign()
        }
    }
}

@Composable
fun AboutScreen(navController: NavController, OnNavigateToIndividual: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        CreateAppBar(title = "Gender", iconImage = Icons.Filled.ArrowBack) {
            navController.popBackStack()
        }
        ProfileScreenDesign(2, OnNavigateToIndividual)
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
            text = "Player : $outputData"
        )
    }
}

fun getCoutriesList(): Map<String, String> {
    var countriesMap = hashMapOf<String, String>()
    val countires = Locale.getISOCountries()
    countires.forEach {
        val locale = Locale("", it)
        countriesMap[locale.country.lowercase(Locale.getDefault())] = locale.displayCountry
    }
    return countriesMap.toList().sortedBy { (_, value) -> value }.toMap()
}