package com.example.torrentclient.domain.usecase

import com.example.torrentclient.domain.models.TorrentListInfo
import com.example.torrentclient.domain.repository.TorrentRepo

class SearchUseCase(private val TorrentRepo: TorrentRepo){
    suspend fun execute(text: String): Array<TorrentListInfo?>
    {
        return TorrentRepo.getTorrents(text = text)
    }
}