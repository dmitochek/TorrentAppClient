package com.example.torrentclient.domain.usecase

import android.content.Context
import com.example.torrentclient.domain.models.NodeServerInfo
import com.example.torrentclient.domain.repository.NodeJSFunctions

class InitNodeServerUseCase(private val nodeJSFunctions: NodeJSFunctions) {
    fun execute(): NodeServerInfo {
        return nodeJSFunctions.initNodeServer()
    }
}