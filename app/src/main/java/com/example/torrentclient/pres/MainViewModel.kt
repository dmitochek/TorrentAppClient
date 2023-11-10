package com.example.torrentclient.pres

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.torrentclient.domain.usecase.ChangeCategoryUseCase
import com.example.torrentclient.domain.usecase.LoadThemeUseCase
import com.example.torrentclient.domain.usecase.SearchUseCase
import com.example.torrentclient.domain.usecase.SendLinkUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.Timer
import kotlin.concurrent.timerTask


class MainViewModel(
    private val searchUseCase: SearchUseCase,
    private val changeCategoryUseCase: ChangeCategoryUseCase,
    private val sendLinkUseCase: SendLinkUseCase,
    private val loadThemeUseCase: LoadThemeUseCase,
) : ViewModel() {
    val livelist = MutableLiveData<ArrayList<ListItemModel>>()
    val loading = MutableLiveData(false)

    val titleCategory = arrayOf(
        "Любая категория",
        "Зарубежные фильмы",
        "Музыка",
        "Другое",
        "Зарубежные сериалы",
        "Наши фильмы",
        "Телевизор",
        "Мультипликация",
        "Игры",
        "Софт",
        "Аниме",
        "Книги",
        "Научно-Популярные",
        "Спорт и здоровье",
        "Хозяйство и быт",
        "Юмор",
        "Наши сериалы",
        "Иностранные релизы"
    )
    external fun startNodeWithArguments(arguments: Array<String?>?): Int?
    var _startedNodeAlready = false

    init {
        if (!_startedNodeAlready) {
            _startedNodeAlready = true

            Thread {
                startNodeWithArguments(
                    arrayOf(
                        "node", "-e",
                        "var http = require('http'); " +
                                "var versions_server = http.createServer( (request, response) => { " +
                                "  response.end('Versions: ' + JSON.stringify(process.versions)); " +
                                "}); " +
                                "versions_server.listen(3000);"
                    )
                )
            }.start()

            Timer().schedule(timerTask {
                CoroutineScope(IO).launch {
                    launch {
                        Task()
                    }
                }
            }, 10000)
        }
    }

    fun Task(){
        var nodeResponse = ""

        nodeResponse = URL("http://127.0.0.1:3000/").readText()

        Log.d("SERVERLALA", nodeResponse)
    }


    //TODO rewriting correctly...

    fun getChangeCategoryUseCase(category: Int) {
        loading.postValue(true)
        var result: ArrayList<ListItemModel>?
        viewModelScope.launch(Dispatchers.IO) {
            result = changeCategoryUseCase.execute(category = category).map {
                ListItemModel(
                    name = it?.name,
                    date = it?.date,
                    size = it?.size,
                    file_link = it?.file_link,
                    lichers = it?.lichers,
                    seeders = it?.seeders
                )
            } as ArrayList<ListItemModel>
            livelist.postValue(result)
        }
    }

    fun getSearchUseCase(input: String) {
        loading.postValue(true)
        var result: ArrayList<ListItemModel>?
        viewModelScope.launch {
            result = searchUseCase.execute(input).map {
                ListItemModel(
                    name = it?.name,
                    date = it?.date,
                    size = it?.size,
                    file_link = it?.file_link,
                    lichers = it?.lichers,
                    seeders = it?.seeders
                )
            } as ArrayList<ListItemModel>
            livelist.postValue(result)
        }
    }

    //Not used any more
    fun executeSendLinkUseCase(link: String, context: Context)
    {
        viewModelScope.launch {
            sendLinkUseCase.execute(link, context)
        }
    }


    fun executeLoadThemeUseCase(): Int
    {
        return loadThemeUseCase.execute()
    }

}