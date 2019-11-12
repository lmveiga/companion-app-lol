package com.gmail.lucasmveigabr.companionlol.di

import android.content.Context
import com.gmail.lucasmveigabr.companionlol.db.room.SummonerDao
import com.gmail.lucasmveigabr.companionlol.networking.repo.SummonerRepo
import com.gmail.lucasmveigabr.companionlol.screens.active_game.ActiveGameViewModel
import com.gmail.lucasmveigabr.companionlol.screens.active_game_list.ActiveGameListViewModel
import com.gmail.lucasmveigabr.companionlol.screens.signup.SummonerSignupViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RoomModule::class, ContextModule::class, RetrofitModule::class])
interface AppComponent {

    fun summonerRepo(): SummonerRepo
    fun appContext(): Context
    fun summonerDao(): SummonerDao
    fun inject(summonerSignupViewModel: SummonerSignupViewModel)
    fun inject(summonerSignupViewModel: ActiveGameListViewModel)
    fun inject(activeGameViewModel: ActiveGameViewModel)


}