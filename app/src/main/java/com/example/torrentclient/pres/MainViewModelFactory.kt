package com.example.torrentclient.pres

import NodeJSFunctionsImpl
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.torrentclient.data.repo.ChangeCategoryImpl
import com.example.torrentclient.data.repo.CurrentThemeRepoImpl
import com.example.torrentclient.data.repo.SendLinkImpl
import com.example.torrentclient.data.repo.TorrentRepoImpl
import com.example.torrentclient.domain.usecase.ChangeCategoryUseCase
import com.example.torrentclient.domain.usecase.InitNodeServerUseCase
import com.example.torrentclient.domain.usecase.LoadThemeUseCase
import com.example.torrentclient.domain.usecase.SearchUseCase
import com.example.torrentclient.domain.usecase.SendLinkUseCase

class MainViewModelFactory(context: Context): ViewModelProvider.Factory{
    private val filmsRepository = TorrentRepoImpl()
    private val searchUseCase = SearchUseCase(TorrentRepo = filmsRepository)

    private val changeCategory = ChangeCategoryImpl()
    private val changeCategoryUseCase = ChangeCategoryUseCase(changeCategory = changeCategory)

    private val sendLink = SendLinkImpl()
    private val sendLinkUseCase = SendLinkUseCase(sendLink)

    private val currentThemeRepo = CurrentThemeRepoImpl(context.applicationContext)
    private val loadThemeUseCase = LoadThemeUseCase(currentThemeRepo)

    private val nodeJSFunctions = NodeJSFunctionsImpl(context.applicationContext)
    private val initNodeServerUseCase = InitNodeServerUseCase(nodeJSFunctions)


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(searchUseCase = searchUseCase,
            changeCategoryUseCase =  changeCategoryUseCase,
            sendLinkUseCase = sendLinkUseCase,
            loadThemeUseCase = loadThemeUseCase,
            initNodeServerUseCase = initNodeServerUseCase) as T
    }
}