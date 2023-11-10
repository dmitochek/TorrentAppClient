package com.example.torrentclient.data.apollo

import com.apollographql.apollo3.ApolloClient

class ApolloServerInit {

    //private val apolloClient = ApolloClient.Builder()
    //    .serverUrl("http://185.223.93.10:4000/")
    //    .build()
    private val apolloClient = ApolloClient.Builder()
        .serverUrl("http://185.223.93.10:4000")
       .build()
    fun init():  ApolloClient{
        return apolloClient
    }
}