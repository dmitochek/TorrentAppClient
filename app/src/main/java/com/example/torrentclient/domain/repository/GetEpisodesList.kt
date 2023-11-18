package com.example.torrentclient.domain.repository

interface GetEpisodesList {
    suspend fun getList(magnet: String?): String?
}