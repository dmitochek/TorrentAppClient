package com.example.torrentclient.domain.usecase

import com.example.torrentclient.domain.repository.CurrentThemeRepo

class SaveThemeUseCase(private val currentThemeRepo: CurrentThemeRepo) {
    fun execute(theme: Int){
        currentThemeRepo.saveTheme(theme)
    }
}