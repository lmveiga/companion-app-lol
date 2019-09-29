package com.gmail.lucasmveigabr.companionlol.di

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ContextModule(private val appContext: Context) {

    @Provides
    fun appContext() = appContext

}