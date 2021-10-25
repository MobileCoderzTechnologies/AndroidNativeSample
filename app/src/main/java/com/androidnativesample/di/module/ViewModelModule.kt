package com.androidnativesample.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androidnativesample.di.annotation.ViewModelKey
import com.androidnativesample.di.factory.ViewModelFactory
import com.androidnativesample.onboard.viewmodel.LoginViewModel
import com.androidnativesample.onboard.viewmodel.WalkthroughViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(WalkthroughViewModel::class)
    abstract fun walkthroughViewModel(viewModel: WalkthroughViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun loginViewModel(viewModel: LoginViewModel): ViewModel
}