package com.example.torrentclient.pres

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.torrentclient.domain.usecase.ChangeCategoryUseCase
import com.example.torrentclient.domain.usecase.LoadThemeUseCase
import com.example.torrentclient.domain.usecase.SearchUseCase
import com.example.torrentclient.domain.usecase.SendLinkUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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