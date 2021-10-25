package com.androidnativesample.onboard.sealed

import com.androidnativesample.onboard.model.User

sealed class LoginState {
    object Idle:LoginState()
    object ShowLoading:LoginState()
    class Success(val user: User?) : LoginState()
    class Error(val throwable: Throwable) : LoginState()
}