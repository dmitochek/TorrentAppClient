package com.example.torrentclient.domain.repository

import android.content.Context
import java.io.File

interface OpenFile {
    fun openFile(context: Context, url: File)
}