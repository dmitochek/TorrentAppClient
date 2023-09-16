package com.example.torrentclient.domain.usecase

import com.example.torrentclient.domain.models.TorrentListInfo

class SearchUseCase(private val TorrentRepo: TorrentRepo){
    fun execute(text: String): Array<TorrentListInfo?>
    {
        return TorrentRepo.getTorrents(text = text)
    }
}