package com.androidnativesample.onboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidnativesample.onboard.model.CommonResponse
import com.androidnativesample.onboard.model.RegisterRequest
import com.androidnativesample.onboard.repository.OnboardRepository
import com.androidnativesample.onboard.sealed.LoginState
import com.androidnativesample.utils.ResultHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class LoginViewModel @Inject constructor(val repository: OnboardRepository): ViewModel() {

    private val _loginState=MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState=_loginState.asStateFlow()

    fun getLoginResponse(request:RegisterRequest){
        _loginState.value=LoginState.ShowLoading
        viewModelScope.launch {
            when(val res=repository.getLoginResponse(request)){
                is ResultHandler.Error -> {
                    _loginState.value=LoginState.Error(res.throwable)
                }
                is ResultHandler.Success -> {
                    val data=(res.data as Response<*>).body() as CommonResponse
                    _loginState.value=LoginState.Success(data.data?.user)
                }
            }
        }
    }
}