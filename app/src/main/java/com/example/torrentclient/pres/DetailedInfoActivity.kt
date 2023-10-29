package com.example.torrentclient.pres

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.torrentclient.R
import com.squareup.picasso.Picasso

class DetailedInfoActivity : AppCompatActivity() {

    private lateinit var vm: DetailedInfoViewModel
    private var loading: Boolean = false

    private var progressBar: ProgressBar? = null
    private lateinit var actionBar: ActionBar
    override fun onCreate(savedInstanceState: Bundle?) {

        vm = ViewModelProvider(this, DetailedInfoViewModelFactory())[DetailedInfoViewModel::class.java]
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_info)

        val imgView: ImageView = findViewById(R.id.mainPhoto)

        progressBar = findViewById(R.id.progress_Bar_Load)
        actionBar = supportActionBar!!
        actionBar.title = intent.getStringExtra("name")

        val textBody: TextView = findViewById(R.id.body)
        vm.livedata.observe(this){ data ->
            progressBar!!.visibility = View.GONE
            textBody.text = data.data
            Picasso.get().load(data.imgs?.get(0)).into(imgView)
            loading = false
        }
        vm.loading.observe(this){ data ->
            loading = data
            progressBar!!.visibility = View.VISIBLE
        }

        val butView: Button = findViewById(R.id.download)

        butView.setOnClickListener{
            intent.getStringExtra("link")?.let {
                    it1 -> vm.executeSendLinkUseCase(it1, this) }
        }

        intent.getStringExtra("link")?.let {
            vm.getLoadDetailedInfoUseCase(it)
        }

    }
}