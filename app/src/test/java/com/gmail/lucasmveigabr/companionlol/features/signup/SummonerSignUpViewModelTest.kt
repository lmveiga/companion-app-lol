package com.gmail.lucasmveigabr.companionlol.features.signup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gmail.lucasmveigabr.companionlol.data.db.dao.SummonerDao
import com.gmail.lucasmveigabr.companionlol.data.model.*
import com.gmail.lucasmveigabr.companionlol.data.model.exception.SummonerNotFoundException
import com.gmail.lucasmveigabr.companionlol.data.model.schema.SummonerSchema
import com.gmail.lucasmveigabr.companionlol.data.repository.SummonerRepo
import com.gmail.lucasmveigabr.companionlol.testutil.TrampolineTestExecutionRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SummonerSignUpViewModelTest {

    @get: Rule
    val rxTestRule = TrampolineTestExecutionRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var summonerRepo: SummonerRepo
    private lateinit var summonerDao: SummonerDao
    private lateinit var sut: SummonerSignUpViewModel

    @Before
    fun setUp() {
        summonerRepo = mockk()
        summonerDao = mockk()
        every { summonerDao.insertSummoner(any()) }.answers { }
        sut = SummonerSignUpViewModel(summonerRepo, summonerDao)
    }

    @Test
    fun `when adding a summoner that exists correct result should be called`() {
        summonerExists()
        sut.addSummoner("summoner", Region.BR)
        assertThat(
            sut.addSummonerResult.value!!,
            `is`(SummonerSignUpViewModel.AddSummonerResult.SUCCESS)
        )
    }

    @Test
    fun `when adding a summoner that exists should insert summoner in database`() {
        summonerExists()
        sut.addSummoner("summoner", Region.BR)
        verify(exactly = 1) { summonerDao.insertSummoner(Summoner("id", "summoner", Region.BR)) }
    }

    @Test
    fun `when adding a summoner that does not exist correct result should be called`() {
        summonerNotExists()
        sut.addSummoner("summoner", Region.BR)
        assertThat(
            sut.addSummonerResult.value!!,
            `is`(SummonerSignUpViewModel.AddSummonerResult.NOT_FOUND)
        )
    }

    @Test
    fun `when adding a summoner and network error happens correct result should be called`() {
        networkError()
        sut.addSummoner("summoner", Region.BR)
        assertThat(
            sut.addSummonerResult.value!!,
            `is`(SummonerSignUpViewModel.AddSummonerResult.NETWORK_ERROR)
        )
    }

    private fun summonerExists() {
        every { summonerRepo.verifyIfSummonerExists(any(), any()) }.answers {
            Result.Success(
                SummonerSchema(
                    1,
                    "summoner",
                    "puuid",
                    1,
                    "accountID",
                    "id",
                    0L
                )
            )
        }
    }

    private fun summonerNotExists() {
        every { summonerRepo.verifyIfSummonerExists(any(), any()) }.answers {
            Result.Failure(SummonerNotFoundException())
        }
    }

    private fun networkError() {
        every { summonerRepo.verifyIfSummonerExists(any(), any()) }.answers {
            Result.Failure(Exception())
        }
    }
}