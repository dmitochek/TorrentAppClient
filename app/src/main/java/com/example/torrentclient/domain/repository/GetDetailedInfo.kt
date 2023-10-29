package com.example.torrentclient.domain.repository

import com.example.torrentclient.domain.models.TorrentDetailedInfo

interface GetDetailedInfo {
    suspend fun getInfo(link: String?): TorrentDetailedInfo?
}