package com.gmail.lucasmveigabr.di

import android.content.Context
import com.gmail.lucasmveigabr.db.room.SummonerDao
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [RoomModule::class, ContextModule::class, RetrofitModule::class ])
interface AppComponent {

    fun summonerDao(): SummonerDao
    fun appContext(): Context
    fun retrofit(): Retrofit
}