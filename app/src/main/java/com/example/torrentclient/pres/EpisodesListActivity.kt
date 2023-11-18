package com.example.torrentclient.pres

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.torrentclient.R

class EpisodesListActivity : AppCompatActivity() {
    private lateinit var vm: EpisodesListViewModel
    private var progressBar: ProgressBar? = null
    private var loading: Boolean = false
    private lateinit var actionBar: ActionBar
    override fun onCreate(savedInstanceState: Bundle?) {

        vm = ViewModelProvider(this, EpisodesListModelFactory())[EpisodesListViewModel::class.java]
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episodes_list)

        progressBar = findViewById(R.id.progress_Bar_Load)
        actionBar = supportActionBar!!
        actionBar.title = intent.getStringExtra("name")
        val tmp: TextView = findViewById(R.id.tmp)

        vm.livedata.observe(this){ data ->
            progressBar!!.visibility = View.GONE
            tmp.text = data
            loading = false
        }
        vm.loading.observe(this){ data ->
            loading = data
            progressBar!!.visibility = View.VISIBLE
        }
        val magnet = intent.getStringExtra("link")
        vm.executeEpisodesListUseCase(magnet)

    }
}