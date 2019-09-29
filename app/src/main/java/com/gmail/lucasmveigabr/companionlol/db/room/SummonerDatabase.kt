package com.gmail.lucasmveigabr.companionlol.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gmail.lucasmveigabr.companionlol.model.Summoner

@Database(version = 1, entities = [Summoner::class], exportSchema = false)
abstract class SummonerDatabase : RoomDatabase() {

    abstract fun summonerDao(): SummonerDao

}