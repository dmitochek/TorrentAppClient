package com.example.torrentclient.domain.repository

interface CurrentThemeRepo {
    fun saveTheme(theme: Int)
    fun getTheme(): Int
}