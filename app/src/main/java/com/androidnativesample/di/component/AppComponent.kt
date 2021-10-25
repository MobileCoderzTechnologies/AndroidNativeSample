package com.androidnativesample.di.component

import com.androidnativesample.MyApplication
import com.androidnativesample.di.module.NetworkModule
import com.androidnativesample.di.module.RepositoryModule
import com.androidnativesample.di.module.ViewModelModule
import com.androidnativesample.onboard.ui.activity.LoginSignupActivity
import com.androidnativesample.onboard.ui.activity.WalkthroughActivity
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [(ViewModelModule::class),(NetworkModule::class),(RepositoryModule::class)])
interface AppComponent: AndroidInjector<MyApplication> {

    fun injectFieldsWalkthrough(walkthroughActivity: WalkthroughActivity)

    fun injectFieldsLoginActivity(walkthroughActivity: LoginSignupActivity)


}