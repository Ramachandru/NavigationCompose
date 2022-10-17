package com.example.navigationcomposeapp

import com.example.navigationcomposeapp.model.PlayerDetails

object CachedPlayerDetails {
    lateinit var listOfPlayers: PlayerDetails
    fun setPlayersDetailsInCache(list: PlayerDetails) {
        listOfPlayers = list
    }

    fun getPlayersDetailsInCache(): PlayerDetails {
        return listOfPlayers
    }
}