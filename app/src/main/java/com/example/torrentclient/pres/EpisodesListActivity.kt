package com.example.torrentclient.pres

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.torrentclient.R
import java.util.Base64


class EpisodesListActivity : AppCompatActivity() {
    private lateinit var vm: EpisodesListViewModel
    private var progressBar: ProgressBar? = null
    private var loading: Boolean = false
    private lateinit var actionBar: ActionBar
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        vm = ViewModelProvider(this, EpisodesListModelFactory())[EpisodesListViewModel::class.java]
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episodes_list)

        progressBar = findViewById(R.id.progress_Bar_Load)
        actionBar = supportActionBar!!
        actionBar.title = intent.getStringExtra("name")
        var arrayAdapter: ArrayAdapter<*>
        var mListView = findViewById<ListView>(R.id.series)

        vm.livedata.observe(this){ data ->
            progressBar!!.visibility = View.GONE
            if (data != null) {
                arrayAdapter = ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data.map{ it.name })
                mListView.adapter = arrayAdapter
            }
            else
                Toast.makeText(this, "Ошибка загрузки!", Toast.LENGTH_SHORT).show()

            loading = false
        }
        vm.loading.observe(this){ data ->
            loading = data
            progressBar!!.visibility = View.VISIBLE
        }
        val magnet = intent.getStringExtra("link")
        vm.executeEpisodesListUseCase(magnet)

        // Link example:
        // http://localhost:3000/stream/bWFnbmV0Oj94dD11cm46YnRpaDoyOGY1M2E0ZDE4OGFlZGJlMWUyZDY1ZDFlOWJhYzJiNDRjZWIxZmFmJmRuPXJ1dG9yLmluZm9fJUQwJUEyJUQwJUI1JUQwJUJCJUQwJUIwKyUyRitCb2RpZXMrJTVCUzAxJTVEKyUyODIwMjMlMjkrV0VCLURMKzcyMHArJTdDK05ld1N0dWRpbyZ0cj11ZHA6Ly9vcGVudG9yLm5ldDo2OTY5JnRyPWh0dHA6Ly9yZXRyYWNrZXIubG9jYWwvYW5ub3VuY2U=/1
        mListView.setOnItemClickListener { parent, _, position, _ ->
            val intent = Intent(Intent.ACTION_VIEW)
            val magnet = Base64.getEncoder().encodeToString(magnet?.toByteArray())
            intent.setDataAndType(Uri.parse(
                "http://localhost:3000/stream/$magnet/$position"), "video/*")
            startActivity(Intent.createChooser(intent, "Complete action using"))
      }

    }
}