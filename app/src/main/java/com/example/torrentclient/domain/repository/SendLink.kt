package com.example.torrentclient.domain.repository

import android.content.Context

interface SendLink {
    suspend fun sendLink(link: String?, context: Context)
}