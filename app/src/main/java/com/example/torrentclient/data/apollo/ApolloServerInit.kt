package com.example.torrentclient.data.apollo

import com.apollographql.apollo3.ApolloClient

class ApolloServerInit {
    private val apolloClient = ApolloClient.Builder()
        .serverUrl("http://10.0.2.2:4000/")
        .build()
    fun init():  ApolloClient{
        return apolloClient
    }
}