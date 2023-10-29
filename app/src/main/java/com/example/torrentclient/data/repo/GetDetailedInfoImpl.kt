package com.example.torrentclient.data.repo

import com.example.torrentclient.data.apollo.ApolloServerInit
import com.example.torrentclient.data.apollo.toSimpleDetailedInfo
import com.example.torrentclient.domain.models.TorrentDetailedInfo
import com.example.torrentclient.domain.repository.GetDetailedInfo
import com.source.AdditionalInfoRutorQuery

class GetDetailedInfoImpl: GetDetailedInfo {
    private suspend fun getData(link: String): AdditionalInfoRutorQuery.Data?{
        val response = ApolloServerInit().init().query(AdditionalInfoRutorQuery(link)).execute()
        return response.data
    }
    override suspend fun getInfo(link: String?): TorrentDetailedInfo? {
        val response = link?.let {
            getData(it)
                ?.additionalInfoRutor
                ?.toSimpleDetailedInfo()
                ?: TorrentDetailedInfo(data = "Not Loaded", imgs = emptyList() )
        }

        return response
    }
}