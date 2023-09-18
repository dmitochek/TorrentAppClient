package com.example.torrentclient.data.repo

import android.util.Log
import com.example.torrentclient.data.apollo.ApolloServerInit
import com.example.torrentclient.domain.repository.SendLink
import com.source.DownloadTorrentQuery
import com.source.GetFilmQuery
import kotlinx.coroutines.runBlocking

class SendLinkImpl: SendLink {
    suspend fun getData(link: String): DownloadTorrentQuery.Data?{
        val response = ApolloServerInit().init().query(DownloadTorrentQuery(link)).execute()
        return response.data
    }
    override fun sendLink(link: String?): Unit = runBlocking{
        val response = link?.let { getData(it) }
            ?.downloadtorrent
        Log.d("RES", response.toString())
    }
}