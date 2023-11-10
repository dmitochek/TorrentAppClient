package com.example.torrentclient.domain.repository

import android.content.Context
import android.content.res.AssetManager
import java.io.File

interface NodeJSFunctions {
    fun deleteFolderRecursively(file: File): Boolean
    fun copyAssetFolder(assetManager: AssetManager, fromAssetPath: String, toPath: String): Boolean
    fun initNodeServer(): String
}