package com.example.torrentclient.pres

import android.content.Context
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.torrentclient.R
import com.example.torrentclient.data.repo.CurrentThemeRepoImpl
import com.example.torrentclient.domain.usecase.LoadThemeUseCase
import com.example.torrentclient.domain.usecase.SaveThemeUseCase
import com.google.android.material.switchmaterial.SwitchMaterial
import android.util.Log


class SettingsActivity : AppCompatActivity() {

    private lateinit var currentThemeRepo: CurrentThemeRepoImpl
    private lateinit var loadThemeUseCase: LoadThemeUseCase
    private lateinit var saveThemeUseCase: SaveThemeUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        currentThemeRepo = CurrentThemeRepoImpl(this.applicationContext)
        loadThemeUseCase = LoadThemeUseCase(currentThemeRepo)
        saveThemeUseCase = SaveThemeUseCase(currentThemeRepo)

        var actionBar: ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Настройки"

        var switchTheme: SwitchMaterial = findViewById(R.id.themeSwitch)

        if (loadThemeUseCase.execute() == 0) {
            switchTheme.isChecked = true
            switchTheme.text = "Темная тема"
        }else{
            switchTheme.isChecked = false
            switchTheme.text = "Светлая тема"
        }


        switchTheme.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!isChecked && buttonView != null) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                saveThemeUseCase.execute(1)
                buttonView.text = "Светлая тема"
            } else if(buttonView != null) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                saveThemeUseCase.execute(0)
                buttonView.text = "Темная тема"
            }
        }

    }



}