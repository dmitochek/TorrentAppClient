package com.example.torrentclient.domain.repository

import com.example.torrentclient.domain.models.TorrentListInfo

interface ChangeCategory {
    fun getTorrents(category: Int): Array<TorrentListInfo?>
}