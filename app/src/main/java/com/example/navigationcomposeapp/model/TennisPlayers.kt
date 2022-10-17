package com.example.navigationcomposeapp.model

import androidx.annotation.StringRes
import com.example.navigationcomposeapp.R

sealed class TennisPlayersState {
    data class SUCCESS(val playersList: List<PlayerDetails>) : TennisPlayersState()
    data class ERROR(val errorMsg: String) : TennisPlayersState()
    object LOADING : TennisPlayersState()
}
sealed class Screen(val Page: String, @StringRes val screenId: Int) {
    object Players : Screen("playerList", R.string.player_list)
    object IndividualPlayer : Screen("playerDetails", R.string.player_details)
    object DummyData : Screen("dummyData", R.string.dummy_data)
}

