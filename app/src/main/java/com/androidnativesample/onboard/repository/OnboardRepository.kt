package com.androidnativesample.onboard.repository

import com.androidnativesample.onboard.model.RegisterRequest
import com.androidnativesample.utils.ResultHandler
import com.androidnativesample.utils.runIO
import com.androidnativesample.webservice.ApiService
import javax.inject.Inject

class OnboardRepository @Inject constructor(val service: ApiService) {

    suspend fun getLoginResponse(request: RegisterRequest):ResultHandler<Any>{
        return runIO {
            service.register(request)
        }
    }
}