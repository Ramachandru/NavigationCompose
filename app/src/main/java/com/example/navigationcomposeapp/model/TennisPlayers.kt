package com.example.navigationcomposeapp.model

import androidx.annotation.StringRes
import com.example.navigationcomposeapp.R

object NavigationKey {
    const val PLAYER_DATA_ARGUMENT: String = "userdata"
    const val PLAYER_DATA: String = "/{userdata}"
    const val DUMMY_DATA_ARGUMENT = "dummy"
    const val DUMMY_DATA = "/{dummy}"
}

//State of Content Loading
sealed class TennisPlayersState {
    data class SUCCESS(val playersList: List<PlayerDetails>) : TennisPlayersState()
    data class ERROR(val errorMsg: String) : TennisPlayersState()
    object LOADING : TennisPlayersState()
}

//Page of Navigation screens
sealed class Screen(val Page: String, @StringRes val screenId: Int) {
    object Players : Screen("Player List", R.string.player_list)
    object IndividualPlayer : Screen("Player Details", R.string.player_details)
    object DummyData : Screen("Dummy Data", R.string.dummy_data)
}

sealed class MainRoute(val route: String) {
    object ProfileScreen : MainRoute("profile")
    object AboutScreen : MainRoute("about")
}
sealed class ProfileRoute(val page : String){
    object Profile : ProfileRoute("Profile")
    object ProfileSub : ProfileRoute("Profile-Sub")
}
sealed class AboutRoute(val Page: String) {
    object About : AboutRoute("About")
}
