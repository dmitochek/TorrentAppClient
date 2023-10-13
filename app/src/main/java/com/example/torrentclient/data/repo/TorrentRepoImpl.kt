package com.example.torrentclient.data.repo

import com.example.torrentclient.data.apollo.ApolloServerInit
import com.example.torrentclient.data.apollo.toSimpleTorrentInfo
import com.example.torrentclient.domain.models.TorrentListInfo
import com.example.torrentclient.domain.usecase.TorrentRepo
import com.source.GetFilmQuery
import kotlinx.coroutines.runBlocking

class TorrentRepoImpl: TorrentRepo {

    suspend fun getData(text: String): GetFilmQuery.Data?{
        val response = ApolloServerInit().init().query(GetFilmQuery(text)).execute()
        return response.data
    }
    override suspend fun getTorrents(text: String): Array<TorrentListInfo?> {
        val response = getData(text)
            ?.getfilm
            ?.map{it?.toSimpleTorrentInfo()}
            ?: emptyList()

        return response.toTypedArray()
    }
}