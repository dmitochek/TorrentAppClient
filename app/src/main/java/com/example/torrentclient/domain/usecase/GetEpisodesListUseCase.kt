package com.example.torrentclient.domain.usecase

import com.example.torrentclient.domain.repository.GetEpisodesList

class GetEpisodesListUseCase(private val getEpisodesList: GetEpisodesList) {
    suspend fun execute(magnet: String): String?
    {
        return getEpisodesList.getList(magnet)
    }

}