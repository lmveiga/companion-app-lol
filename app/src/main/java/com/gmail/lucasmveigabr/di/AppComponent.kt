package com.gmail.lucasmveigabr.di

import android.content.Context
import com.gmail.lucasmveigabr.db.room.SummonerDao
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RoomModule::class, ContextModule::class])
interface AppComponent {

    fun summonerDao(): SummonerDao
    fun appContext(): Context

}