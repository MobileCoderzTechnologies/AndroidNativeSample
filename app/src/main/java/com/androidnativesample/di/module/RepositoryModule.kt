package com.androidnativesample.di.module

import com.androidnativesample.onboard.repository.OnboardRepository
import com.androidnativesample.webservice.ApiService
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun  getOnboardRepository(service:ApiService):OnboardRepository{
        return OnboardRepository(service)
    }
}