package com.example.torrentclient.domain.usecase

import com.example.torrentclient.domain.models.TorrentListInfo

interface TorrentRepo {
    fun getTorrents(text: String): Array<TorrentListInfo?>
}