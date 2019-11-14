package com.gmail.lucasmveigabr.companionlol.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gmail.lucasmveigabr.companionlol.model.Summoner

@Dao
interface SummonerDao {

    @Query("SELECT * FROM SUMMONER ORDER BY summonnerName")
    fun getSummoners(): LiveData<List<Summoner>>

    @Query("SELECT COUNT(*) FROM SUMMONER ORDER BY summonnerName")
    fun getSummonerCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSummoner(summoner: Summoner)

    @Delete
    fun deleteSummoner(summoner: Summoner)

}