package com.example.navigationcomposeapp.network

import com.example.navigationcomposeapp.model.Players
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.GET
import javax.inject.Inject

class RemoteDataSource @Inject constructor(val apiInterface: ApiInterface) {
    suspend fun getPlayers(): Flow<Players> = flow {
        emit(apiInterface.getPlayersData())
    }

    interface ApiInterface {
        companion object {
            const val BASE_URL: String = "https://demonuts.com/Demonuts/JsonTest/Tennis/"
        }

        @GET("json_parsing.php")
        suspend fun getPlayersData(): Players
    }
}