package com.androidnativesample.onboard.model

data class RegisterRequest(
    var phoneNumber:String,
    var countryCode:String,
    var loginType:String,
    )