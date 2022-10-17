package com.example.navigationcomposeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigationcomposeapp.model.TennisPlayersState
import com.example.navigationcomposeapp.network.RemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerListViewModel @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    ViewModel() {
    private var _playerData = MutableStateFlow<TennisPlayersState>(TennisPlayersState.LOADING)
    val playerData = _playerData

    init {
        viewModelScope.launch {
            getDataFromRemote()
        }
    }

    private suspend fun getDataFromRemote() {
        _playerData.value = TennisPlayersState.LOADING
        remoteDataSource.getPlayers()
            .flowOn(Dispatchers.IO)
            .catch { exception ->
                _playerData.value = TennisPlayersState.ERROR(exception.message!!)
            }
            .collect {
                _playerData.value = TennisPlayersState.SUCCESS(it.data)
            }
    }
}
