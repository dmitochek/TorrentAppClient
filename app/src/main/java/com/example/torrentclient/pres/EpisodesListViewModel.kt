package com.example.torrentclient.pres

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.torrentclient.domain.usecase.GetEpisodesListUseCase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class User(val name: String, val length: Long)
class EpisodesListViewModel (
    private val getEpisodesListUseCase: GetEpisodesListUseCase
): ViewModel(){

    val livedata = MutableLiveData<List<User>?>()
    val loading = MutableLiveData(false)
    fun executeEpisodesListUseCase(magnet: String?){
        loading.postValue(true)
        var result: String?
        var arr: List<User>?

        val gson = Gson()
        val itemType = object : TypeToken<List<User>?>() {}.type
        viewModelScope.launch(Dispatchers.IO) {
            result = magnet?.let { getEpisodesListUseCase.execute(magnet = it) }

            arr = gson.fromJson<List<User>?>(result, itemType)
            //Log.d("fuck", result?.let { Json.decodeFromString<List<User>>(it) }.toString())
            //Log.d("ARRAY", arr.toString())
            livedata.postValue(arr)
        }
    }
}