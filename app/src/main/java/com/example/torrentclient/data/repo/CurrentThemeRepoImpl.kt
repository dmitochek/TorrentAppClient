package com.example.torrentclient.data.repo

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.torrentclient.domain.repository.CurrentThemeRepo


private const val SHARED_PREFS_NAME = "shared_prefs"
private const val KEY_THEME = "theme"
class CurrentThemeRepoImpl(context: Context): CurrentThemeRepo {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    override fun saveTheme(theme: Int) {
        sharedPreferences.edit().putInt(KEY_THEME, theme).apply()
    }

    override fun getTheme(): Int {
        return sharedPreferences.getInt(KEY_THEME, 0)
    }

}