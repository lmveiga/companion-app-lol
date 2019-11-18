package com.gmail.lucasmveigabr.companionlol.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gmail.lucasmveigabr.companionlol.core.navigation.NavigationViewModel
import com.gmail.lucasmveigabr.companionlol.core.viewmodel.ViewModelFactory
import com.gmail.lucasmveigabr.companionlol.core.viewmodel.ViewModelKey
import com.gmail.lucasmveigabr.companionlol.features.activegame.ActiveGameViewModel
import com.gmail.lucasmveigabr.companionlol.features.activegamelist.ActiveGameListViewModel
import com.gmail.lucasmveigabr.companionlol.features.signup.SummonerSignUpViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ActiveGameListViewModel::class)
    internal abstract fun provideActiveGameListViewModel(viewModel: ActiveGameListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ActiveGameViewModel::class)
    internal abstract fun provideActiveGameViewModel(viewModel: ActiveGameViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NavigationViewModel::class)
    internal abstract fun provideNavigationViewModel(viewModel: NavigationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SummonerSignUpViewModel::class)
    internal abstract fun provideSummonerSignUpViewModel(viewModel: SummonerSignUpViewModel): ViewModel
}