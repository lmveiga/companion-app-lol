package com.gmail.lucasmveigabr.companionlol

import android.app.Application
import com.gmail.lucasmveigabr.companionlol.di.AppComponent
import com.gmail.lucasmveigabr.companionlol.di.ContextModule
import com.gmail.lucasmveigabr.companionlol.di.DaggerAppComponent

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