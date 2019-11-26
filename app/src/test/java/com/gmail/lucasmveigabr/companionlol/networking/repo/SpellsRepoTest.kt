package com.gmail.lucasmveigabr.companionlol.networking.repo

import com.gmail.lucasmveigabr.companionlol.data.model.Result
import com.gmail.lucasmveigabr.companionlol.data.repository.SpellsRepo
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test

class SpellsRepoTest {

    private lateinit var sut: SpellsRepo
    private lateinit var api: LeagueApiTd

    @Before
    fun setUp() {
        api = LeagueApiTd()
        sut = SpellsRepo(api)
    }

    @Test
    fun `when getSpell is called and api returns successful response should return correct spell`() {
        val result = sut.getSpell(21)
        assertThat(result, instanceOf(Result.Success::class.java))
        assertThat((result as Result.Success).data.key, `is`("21"))
    }

    @Test
    fun `when getSpell is called and there is cache value should return cache data`() {
        sut.getSpell(1)
        api.hasBeenCalled = false
        val result = sut.getSpell(1)
        assertThat(api.hasBeenCalled, `is`(false))
        assertThat(result, instanceOf(Result.Success::class.java))
        assertThat((result as Result.Success).data.key, `is`(1.toString()))
    }

    @Test
    fun `when get spell is called and an network error occurs should return failure`() {
        api.networkError = true
        val result = sut.getSpell(1)
        assertThat(result, instanceOf(Result.Failure::class.java))
    }

    @Test
    fun `when get spell is called and other error occur should correctly return failure`() {
        api.otherError = true
        val result = sut.getSpell(1)
        assertThat(result, instanceOf(Result.Failure::class.java))
    }

    @Test
    fun `when get spell is called with an invalid ID should return failure correctly`() {
        val result = sut.getSpell(11231)
        assertThat(result, instanceOf(Result.Failure::class.java))
    }

}