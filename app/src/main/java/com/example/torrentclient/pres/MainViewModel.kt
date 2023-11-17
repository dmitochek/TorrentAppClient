package com.example.torrentclient.pres

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.torrentclient.domain.usecase.ChangeCategoryUseCase
import com.example.torrentclient.domain.usecase.InitNodeServerUseCase
import com.example.torrentclient.domain.usecase.LoadThemeUseCase
import com.example.torrentclient.domain.usecase.SearchUseCase
import com.example.torrentclient.domain.usecase.SendLinkUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.net.URL
import java.util.Timer
import kotlin.concurrent.timerTask


class MainViewModel(
    private val searchUseCase: SearchUseCase,
    private val changeCategoryUseCase: ChangeCategoryUseCase,
    private val sendLinkUseCase: SendLinkUseCase,
    private val loadThemeUseCase: LoadThemeUseCase,
    private val initNodeServerUseCase: InitNodeServerUseCase
) : ViewModel() {
    val livelist = MutableLiveData<ArrayList<ListItemModel>>()
    val loading = MutableLiveData(false)

    private external fun startNodeWithArguments(arguments: Array<String?>?): Int?

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
    private var _startedNodeAlready = false

    init {
        if (!_startedNodeAlready) {
            _startedNodeAlready = true
            Thread {
                val nodeSerInfo = initNodeServerUseCase.execute()

                startNodeWithArguments(
                    arrayOf(
                        "node",
                        "${nodeSerInfo.nodeDir}/main.js",
                        "--path=${nodeSerInfo.path}"
                    )
                )
            }.start()
        }
    }

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