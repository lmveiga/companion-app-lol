package com.gmail.lucasmveigabr.companionlol.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gmail.lucasmveigabr.companionlol.core.viewmodel.ViewModelFactory
import com.gmail.lucasmveigabr.companionlol.core.viewmodel.ViewModelKey
import com.gmail.lucasmveigabr.companionlol.screen.activegamelist.ActiveGameListViewModel
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
}