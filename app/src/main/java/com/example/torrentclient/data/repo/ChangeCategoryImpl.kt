package com.example.torrentclient.data.repo

import com.example.torrentclient.data.apollo.ApolloServerInit
import com.example.torrentclient.data.apollo.toSimpleTorrentInfo
import com.example.torrentclient.domain.models.TorrentListInfo
import com.example.torrentclient.domain.repository.ChangeCategory
import com.source.GetcategoryfilmsQuery
import kotlinx.coroutines.runBlocking

class ChangeCategoryImpl: ChangeCategory {
    suspend fun getData(category: Int): GetcategoryfilmsQuery.Data?{
        val response = ApolloServerInit().init().query(GetcategoryfilmsQuery(category)).execute()
        return response.data
    }
    override fun getTorrents(category: Int): Array<TorrentListInfo?> = runBlocking {
        val response = getData(category)
            ?.getcategoryfilms
            ?.map{it?.toSimpleTorrentInfo()}
            ?: emptyList()

        return@runBlocking response.toTypedArray()
    }

}