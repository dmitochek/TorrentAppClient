package com.example.torrentclient.domain.usecase

import com.example.torrentclient.domain.models.TorrentDetailedInfo
import com.example.torrentclient.domain.repository.GetDetailedInfo

class LoadDetailedInfoUseCase(private val getDetailedInfo: GetDetailedInfo) {
    suspend fun execute(link: String): TorrentDetailedInfo? {
        return getDetailedInfo.getInfo(link)
    }
}