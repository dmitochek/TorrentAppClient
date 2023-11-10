package com.example.torrentclient.domain.usecase

import android.content.Context
import com.example.torrentclient.domain.repository.NodeJSFunctions

class InitNodeServerUseCase(private val nodeJSFunctions: NodeJSFunctions) {
    fun execute(): String{
        return nodeJSFunctions.initNodeServer()
    }
}