package com.gmail.lucasmveigabr.companionlol.db.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gmail.lucasmveigabr.companionlol.model.Summoner

@Dao
interface SummonerDao {

    @Query("SELECT * FROM SUMMONER ORDER BY summonnerName")
    fun getSummoners(): LiveData<List<Summoner>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSummoner(summoner: Summoner)

}