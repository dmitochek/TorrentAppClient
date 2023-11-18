package com.example.torrentclient.pres

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.torrentclient.domain.usecase.GetEpisodesListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EpisodesListViewModel (
    private val getEpisodesListUseCase: GetEpisodesListUseCase
): ViewModel(){

    val livedata = MutableLiveData<String?>()
    val loading = MutableLiveData(false)
    fun executeEpisodesListUseCase(magnet: String?){
        loading.postValue(true)
        var result: String?
        viewModelScope.launch(Dispatchers.IO) {
            result = magnet?.let { getEpisodesListUseCase.execute(magnet = it) }
            livedata.postValue(result)
        }
    }
}