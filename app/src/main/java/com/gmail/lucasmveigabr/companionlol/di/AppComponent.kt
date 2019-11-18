package com.gmail.lucasmveigabr.companionlol.di

import com.gmail.lucasmveigabr.companionlol.data.db.dao.SummonerDao
import com.gmail.lucasmveigabr.companionlol.features.activegame.ActiveGameFragment
import com.gmail.lucasmveigabr.companionlol.features.activegame.ActiveGameViewModel
import com.gmail.lucasmveigabr.companionlol.features.activegamelist.ActiveGameListFragment
import com.gmail.lucasmveigabr.companionlol.features.activegamelist.ActiveGameListViewModel
import com.gmail.lucasmveigabr.companionlol.features.main.MainActivity
import com.gmail.lucasmveigabr.companionlol.features.signup.SummonerSignUpViewModel
import com.gmail.lucasmveigabr.companionlol.features.signup.SummonerSignUpFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RoomModule::class, ContextModule::class, RetrofitModule::class, ViewModelModule::class])
interface AppComponent {

    fun summonerDao(): SummonerDao
    fun inject(summonerSignUpViewModel: SummonerSignUpViewModel)
    fun inject(activeGameListViewModel: ActiveGameListViewModel)
    fun inject(activeGameViewModel: ActiveGameViewModel)
    fun inject(fragment: ActiveGameListFragment)
    fun inject(fragment: ActiveGameFragment)
    fun inject(fragment: SummonerSignUpFragment)
    fun inject(activity: MainActivity)


}