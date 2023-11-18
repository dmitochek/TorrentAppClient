package com.example.torrentclient.pres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.torrentclient.data.nodejs.GetEpisodesListImpl
import com.example.torrentclient.domain.usecase.GetEpisodesListUseCase

class EpisodesListModelFactory(): ViewModelProvider.Factory {
    private val getEpisodesList = GetEpisodesListImpl()
    private val getEpisodesListUseCase = GetEpisodesListUseCase(getEpisodesList = getEpisodesList)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EpisodesListViewModel(
            getEpisodesListUseCase = getEpisodesListUseCase
        ) as T
    }

}