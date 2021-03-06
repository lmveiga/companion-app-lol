package com.gmail.lucasmveigabr.companionlol.di

import android.content.Context
import androidx.room.Room
import com.gmail.lucasmveigabr.companionlol.data.db.SummonerDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(context: Context) =
        Room.databaseBuilder(context, SummonerDatabase::class.java, "summoners.db")
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    @Singleton
    fun providesSummonersDao(database: SummonerDatabase) =
        database.summonerDao()

}