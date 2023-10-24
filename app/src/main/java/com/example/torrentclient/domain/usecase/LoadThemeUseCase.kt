package com.example.torrentclient.domain.usecase

import com.example.torrentclient.domain.repository.CurrentThemeRepo

class LoadThemeUseCase(private val currentThemeRepo: CurrentThemeRepo) {
    fun execute(): Int{
        return currentThemeRepo.getTheme()
    }

}