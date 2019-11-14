package com.gmail.lucasmveigabr.companionlol.di

import com.gmail.lucasmveigabr.companionlol.data.db.dao.SummonerDao
import com.gmail.lucasmveigabr.companionlol.screen.activegame.ActiveGameViewModel
import com.gmail.lucasmveigabr.companionlol.screen.activegamelist.ActiveGameListViewModel
import com.gmail.lucasmveigabr.companionlol.screen.signup.SummonerSignupViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RoomModule::class, ContextModule::class, RetrofitModule::class])
interface AppComponent {

    fun summonerDao(): SummonerDao
    fun inject(summonerSignupViewModel: SummonerSignupViewModel)
    fun inject(summonerSignupViewModel: ActiveGameListViewModel)
    fun inject(activeGameViewModel: ActiveGameViewModel)


}