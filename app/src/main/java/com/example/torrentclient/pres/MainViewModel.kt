package com.example.torrentclient.pres

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.torrentclient.domain.usecase.ChangeCategoryUseCase
import com.example.torrentclient.domain.usecase.SearchUseCase
import com.example.torrentclient.domain.usecase.SendLinkUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.BooleanExpression
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val searchUseCase: SearchUseCase,
    private val changeCategoryUseCase: ChangeCategoryUseCase,
    private val sendLinkUseCase: SendLinkUseCase
) : ViewModel() {
    val livelist = MutableLiveData<ArrayList<ListItemModel>>()

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
        var result: ArrayList<ListItemModel>? = null
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
        var result: ArrayList<ListItemModel>? = null
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

    fun executeSendLinkUseCase(link: String, context: Context)
    {
        viewModelScope.launch {
            sendLinkUseCase.execute(link, context)
        }
    }

}