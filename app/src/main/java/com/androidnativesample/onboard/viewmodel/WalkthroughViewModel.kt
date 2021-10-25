package com.androidnativesample.onboard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidnativesample.utils.ResultHandler
import com.androidnativesample.utils.runIO
import com.androidnativesample.webservice.ApiService
import kotlinx.coroutines.launch
import javax.inject.Inject

class WalkthroughViewModel @Inject constructor(val service: ApiService): ViewModel() {

    private var _data:MutableLiveData<String>?=MutableLiveData()
    var data:LiveData<String>?=_data

    init {
        viewModelScope.launch {
            val res=runIO {
                //service.getCategories(2,1,"items[id,name,custom_attributes[image]]total_count")
            }
            when(res){
                is ResultHandler.Error->{
                    _data?.postValue(res.throwable.message)
                }
                else->{
                    _data?.postValue(res.toString())
                }
            }

        }
    }
}