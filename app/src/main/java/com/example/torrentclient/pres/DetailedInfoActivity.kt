package com.example.torrentclient.pres

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.torrentclient.R
import com.smarteist.autoimageslider.SliderView

class DetailedInfoActivity : AppCompatActivity() {

    private lateinit var vm: DetailedInfoViewModel
    private var loading: Boolean = false

    private var progressBar: ProgressBar? = null
    private lateinit var actionBar: ActionBar

    lateinit var sliderView: SliderView
    lateinit var sliderAdapter: SliderAdapter
    override fun onCreate(savedInstanceState: Bundle?) {

        vm = ViewModelProvider(this, DetailedInfoViewModelFactory())[DetailedInfoViewModel::class.java]
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_info)

        sliderView = findViewById(R.id.slider)

        progressBar = findViewById(R.id.progress_Bar_Load)
        actionBar = supportActionBar!!
        actionBar.title = intent.getStringExtra("name")

        val textBody: TextView = findViewById(R.id.body)
        vm.livedata.observe(this){ data ->
            progressBar!!.visibility = View.GONE
            textBody.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(data.data, Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(data.data)
            }

            sliderAdapter = ArrayList(data.imgs)?.let { SliderAdapter(it) } !!
            sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
            sliderView.setSliderAdapter(sliderAdapter)
            sliderView.scrollTimeInSec = 3
            sliderView.isAutoCycle = true
            sliderView.startAutoCycle()
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