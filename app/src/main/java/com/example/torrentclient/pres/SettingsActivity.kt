package com.example.torrentclient.pres

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.torrentclient.R
import com.google.android.material.switchmaterial.SwitchMaterial


class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        var actionBar: ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Настройки"

        var switchTheme: SwitchMaterial = findViewById(R.id.themeSwitch)

        switchTheme.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                if (buttonView != null) {
                    buttonView.text = "Светлая тема"
                }
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                if (buttonView != null) {
                    buttonView.text = "Темная тема"
                }
            }
        }

    }


}