package com.gmail.lucasmveigabr

import android.app.Application
import com.gmail.lucasmveigabr.di.AppComponent
import com.gmail.lucasmveigabr.di.ContextModule
import com.gmail.lucasmveigabr.di.DaggerAppComponent

class App: Application() {

    companion object {
        var appComponent: AppComponent? = null
    }



    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .contextModule(ContextModule(this))
            .build()
    }


}