package com.example.torrentclient.domain.repository

import com.example.torrentclient.domain.models.TorrentListInfo

interface TorrentRepo {
    suspend fun getTorrents(text: String): Array<TorrentListInfo?>
}