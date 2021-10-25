package com.androidnativesample.onboard.model

data class CommonResponse (
    var status:String,
    var message:String,
    var data: Data?
    )

data class Data(
    var user: User?
)

data class User(
    var name:String?,
    var countryCode:String?,
    var phoneNumber:String?,
    var isPhoneVerified:Boolean?,
    var email:String?,
    var isEmailVerified:Boolean?,
    var avatar:String?,
    var isAvatarVerified:Boolean?,
    var isAccountActive:Boolean?,
    var loginType:String?,
    var _id:String?,
    var createdAt:String?,
    var updatedAt:String?,
    var __v:String?
)