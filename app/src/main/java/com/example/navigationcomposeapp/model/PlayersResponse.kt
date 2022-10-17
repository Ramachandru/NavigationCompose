package com.example.navigationcomposeapp.model

data class Players(
    val status: String,
    val message: String,
    val data: List<PlayerDetails>
)

data class PlayerDetails(
    val id: String,
    val name: String,
    val country: String,
    val city: String,
    val imgURL: String
)