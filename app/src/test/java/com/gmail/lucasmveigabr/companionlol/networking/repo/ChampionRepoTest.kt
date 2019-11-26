package com.gmail.lucasmveigabr.companionlol.networking.repo

import com.gmail.lucasmveigabr.companionlol.data.model.Result
import com.gmail.lucasmveigabr.companionlol.data.repository.ChampionRepo
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ChampionRepoTest {

    private lateinit var sut: ChampionRepo
    private lateinit var api: LeagueApiTd


    @Before
    fun setup() {
        api = LeagueApiTd()
        sut = ChampionRepo(api)
    }

    @Test
    fun `when get champion is called and api returns successful response should correctly return success`() {
        val result = sut.getChampion(103)
        assertThat(result, instanceOf(Result.Success::class.java))
        assertThat((result as Result.Success).data.name, `is`("Ahri"))
    }

    @Test
    fun `when get champion is called and there is cached value should return cached value correctly`() {
        sut.getChampion(103)
        api.hasBeenCalled = false
        val result = sut.getChampion(103)
        assertThat(api.hasBeenCalled, `is`(false))
        assertThat(result, instanceOf(Result.Success::class.java))
        assertThat((result as Result.Success).data.name, `is`("Ahri"))
    }

    @Test
    fun `when get champion is called and api returns a network error should correctly return failure`() {
        api.networkError = true
        val result = sut.getChampion(103)
        assertThat(result, instanceOf(Result.Failure::class.java))
    }

    @Test
    fun `when getChampion is called and api return error should correctly return failure`() {
        api.otherError = true
        val result = sut.getChampion(103)
        assertThat(result, instanceOf(Result.Failure::class.java))
    }

    @Test
    fun `when getChampion is called with incorrect ID should correctly return error`() {
        val result = sut.getChampion(4124)
        assertThat(result, instanceOf(Result.Failure::class.java))
    }

}