package com.example.torrentclient.data.apollo

import com.example.torrentclient.domain.models.TorrentListInfo
import com.source.GetFilmQuery

fun GetFilmQuery.Getfilm.toSimpleTorrentInfo(): TorrentListInfo{
    return TorrentListInfo(
        date = date,
        name = name,
        file_link = file_link,
        size = size,
        error = error
    )
}