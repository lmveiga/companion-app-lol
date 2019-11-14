package com.gmail.lucasmveigabr.companionlol.networking.repo

import com.gmail.lucasmveigabr.companionlol.data.repository.SpellsRepo
import com.gmail.lucasmveigabr.companionlol.model.Result
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class SpellsRepoTest {

    lateinit var SUT: SpellsRepo
    lateinit var api: LeagueApiTd

    @Before
    fun setUp() {
        api = LeagueApiTd()
        SUT = SpellsRepo(api)
    }

    @Test
    fun getSpell_SuccessfulResponse_ReturnsCorrectSpell(){
        val result = SUT.getSpell(21)
        assertThat(result, instanceOf(Result.Success::class.java))
        assertThat((result as Result.Success).data.key, `is`("21"))
    }

    @Test
    fun getSpell_SuccessfulResponseAfterCache_ReturnsCachedSpell(){
        SUT.getSpell(1)
        api.hasBeenCalled = false
        val result = SUT.getSpell(1)
        assertThat(api.hasBeenCalled, `is`(false))
        assertThat(result, instanceOf(Result.Success::class.java))
        assertThat((result as Result.Success).data.key, `is`(1.toString()))
    }

    @Test
    fun getSpell_NetworkError_ReturnsCorrectResponse(){
        api.networkError = true
        val result = SUT.getSpell(1)
        assertThat(result, instanceOf(Result.Failure::class.java))
    }

    @Test
    fun getSpell_Other_ReturnsCorrectResponse(){
        api.otherError = true
        val result = SUT.getSpell(1)
        assertThat(result, instanceOf(Result.Failure::class.java))
    }

    @Test
    fun getSpell_InvalidId_ReturnsCorrectResponse(){
        val result = SUT.getSpell(11231)
        assertThat(result, instanceOf(Result.Failure::class.java))
    }

}