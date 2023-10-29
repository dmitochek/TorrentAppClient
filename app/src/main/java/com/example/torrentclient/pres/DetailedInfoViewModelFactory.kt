package com.example.torrentclient.pres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.torrentclient.data.repo.GetDetailedInfoImpl
import com.example.torrentclient.data.repo.SendLinkImpl
import com.example.torrentclient.domain.usecase.LoadDetailedInfoUseCase
import com.example.torrentclient.domain.usecase.SendLinkUseCase

class DetailedInfoViewModelFactory(): ViewModelProvider.Factory {
    private val getDetailedInfo = GetDetailedInfoImpl()
    private val loadDetailedInfoUseCase = LoadDetailedInfoUseCase(getDetailedInfo = getDetailedInfo)

    private val sendLink = SendLinkImpl()
    private val sendLinkUseCase = SendLinkUseCase(sendLink)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailedInfoViewModel(
            loadDetailedInfoUseCase = loadDetailedInfoUseCase,
            sendLinkUseCase = sendLinkUseCase
            ) as T
    }
}