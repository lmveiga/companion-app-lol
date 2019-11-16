package com.gmail.lucasmveigabr.companionlol.di

import android.app.Activity
import androidx.fragment.app.Fragment
import com.gmail.lucasmveigabr.companionlol.data.db.dao.SummonerDao
import com.gmail.lucasmveigabr.companionlol.screen.activegame.ActiveGameFragment
import com.gmail.lucasmveigabr.companionlol.screen.activegame.ActiveGameViewModel
import com.gmail.lucasmveigabr.companionlol.screen.activegamelist.ActiveGameListFragment
import com.gmail.lucasmveigabr.companionlol.screen.activegamelist.ActiveGameListViewModel
import com.gmail.lucasmveigabr.companionlol.screen.main.MainActivity
import com.gmail.lucasmveigabr.companionlol.screen.signup.SummonerSignUpViewModel
import com.gmail.lucasmveigabr.companionlol.screen.signup.SummonerSignupFragment
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
    fun inject(fragment: SummonerSignupFragment)
    fun inject(activity: MainActivity)


}