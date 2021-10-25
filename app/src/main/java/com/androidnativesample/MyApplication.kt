package com.androidnativesample

import android.app.Application
import com.androidnativesample.di.component.AppComponent

class MyApplication: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance=this
    }

    companion object{

        lateinit var instance:MyApplication
    }

}