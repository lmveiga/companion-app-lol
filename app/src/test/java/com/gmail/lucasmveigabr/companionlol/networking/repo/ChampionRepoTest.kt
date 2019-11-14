package com.gmail.lucasmveigabr.companionlol.networking.repo

import com.gmail.lucasmveigabr.companionlol.data.repository.ChampionRepo
import com.gmail.lucasmveigabr.companionlol.model.Result
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ChampionRepoTest{

    lateinit var SUT: ChampionRepo

    lateinit var api: LeagueApiTd


    @Before
    fun setup(){
        api = LeagueApiTd()
        SUT = ChampionRepo(api)
    }

    @Test
    fun getChampion_SuccessfulResponse_ReturnsCorrectChampion() {
        val result = SUT.getChampion(103)
        assertThat(result, instanceOf(Result.Success::class.java))
        assertThat((result as Result.Success).data.name, `is`("Ahri"))
    }

    @Test
    fun getChampion_SuccessfulResponseAfterCache_ReturnsCachedChampion() {
        SUT.getChampion(103)
        api.hasBeenCalled = false
        val result = SUT.getChampion(103)
        assertThat(api.hasBeenCalled, `is`(false))
        assertThat(result, instanceOf(Result.Success::class.java))
        assertThat((result as Result.Success).data.name, `is`("Ahri"))
    }

    @Test
    fun getChampion_NetworkError_ReturnsFailure(){
        api.networkError = true
        val result = SUT.getChampion(103)
        assertThat(result, instanceOf(Result.Failure::class.java))
    }

    @Test
    fun getChampion_OtherError_ReturnsFailure(){
        api.otherError = true
        val result = SUT.getChampion(103)
        assertThat(result, instanceOf(Result.Failure::class.java))
    }

    @Test
    fun getChampion_InvalidId_ReturnsFailure(){
        val result = SUT.getChampion(4124)
        assertThat(result, instanceOf(Result.Failure::class.java))
    }

}