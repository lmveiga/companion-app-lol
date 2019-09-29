package com.gmail.lucasmveigabr.companionlol.di

import android.content.Context
import com.gmail.lucasmveigabr.companionlol.networking.repo.SummonerRepo
import com.gmail.lucasmveigabr.companionlol.signup.SummonerSignupViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RoomModule::class, ContextModule::class, RetrofitModule::class ])
interface AppComponent {

    fun summonerRepo(): SummonerRepo
    fun appContext(): Context
    fun inject(summonerSignupViewModel: SummonerSignupViewModel)


}