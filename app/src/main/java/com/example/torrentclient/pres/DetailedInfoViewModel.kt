package com.example.torrentclient.pres

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.torrentclient.domain.models.TorrentDetailedInfo
import com.example.torrentclient.domain.usecase.LoadDetailedInfoUseCase
import com.example.torrentclient.domain.usecase.SendLinkUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailedInfoViewModel(
    private val loadDetailedInfoUseCase: LoadDetailedInfoUseCase,
    private val sendLinkUseCase: SendLinkUseCase
): ViewModel() {

    val livedata = MutableLiveData<TorrentDetailedInfo>()
    val loading = MutableLiveData(false)
    fun getLoadDetailedInfoUseCase(link: String){
        loading.postValue(true)
        var result: TorrentDetailedInfo?
        viewModelScope.launch(Dispatchers.IO) {
            result = loadDetailedInfoUseCase.execute(link = link)
            livedata.postValue(result)
        }
    }

    fun executeSendLinkUseCase(link: String, context: Context)
    {
        viewModelScope.launch {
            sendLinkUseCase.execute(link, context)
        }
    }

}
