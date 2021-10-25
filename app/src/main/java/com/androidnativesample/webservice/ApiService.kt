package com.androidnativesample.webservice

import com.androidnativesample.onboard.model.CommonResponse
import com.androidnativesample.onboard.model.RegisterRequest
import com.androidnativesample.utils.AppConstants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST(AppConstants.REGISTER_API)
    suspend fun register(@Body body: RegisterRequest): Response<CommonResponse?>

}