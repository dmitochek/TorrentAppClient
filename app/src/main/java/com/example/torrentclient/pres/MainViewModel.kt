package com.example.torrentclient.pres

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.torrentclient.data.repo.ChangeCategoryImpl
import com.example.torrentclient.data.repo.SendLinkImpl
import com.example.torrentclient.data.repo.TorrentRepoImpl
import com.example.torrentclient.domain.usecase.ChangeCategoryUseCase
import com.example.torrentclient.domain.usecase.SearchUseCase
import com.example.torrentclient.domain.usecase.SendLinkUseCase

class MainViewModel(
    private val searchUseCase: SearchUseCase,
    private val changeCategoryUseCase: ChangeCategoryUseCase,
    private val sendLinkUseCase: SendLinkUseCase
) : ViewModel() {

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
    fun getSearchUseCase(input: String): ArrayList<ListItemModel>{
        return searchUseCase.execute(input).map{ ListItemModel(name = it?.name, date = it?.date,
            size = it?.size, file_link = it?.file_link, lichers = it?.lichers, seeders = it?.seeders)} as ArrayList<ListItemModel>
    }

    fun getChangeCategoryUseCase(category: Int): ArrayList<ListItemModel> {
        return changeCategoryUseCase.execute(category = category).map{ ListItemModel(name = it?.name, date = it?.date,
            size = it?.size, file_link = it?.file_link, lichers = it?.lichers, seeders = it?.seeders)} as ArrayList<ListItemModel>
    }

    fun executeSendLinkUseCase(link: String, context: Context)
    {
        sendLinkUseCase.execute(link, context)
    }

}