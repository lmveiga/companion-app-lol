package com.gmail.lucasmveigabr.companionlol.features.activegamelist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gmail.lucasmveigabr.companionlol.data.db.dao.SummonerDao
import com.gmail.lucasmveigabr.companionlol.data.model.Region
import com.gmail.lucasmveigabr.companionlol.data.model.Result
import com.gmail.lucasmveigabr.companionlol.data.model.Summoner
import com.gmail.lucasmveigabr.companionlol.data.model.exception.SummonerNotInMatchException
import com.gmail.lucasmveigabr.companionlol.data.repository.SummonerRepo
import com.gmail.lucasmveigabr.companionlol.testutil.TrampolineTestExecutionRule
import com.gmail.lucasmveigabr.companionlol.testutil.data.GameData.getListOfSummoners
import com.gmail.lucasmveigabr.companionlol.testutil.data.GameData.getParticipants
import com.gmail.lucasmveigabr.companionlol.testutil.data.GameData.getSummonerMatchStatus
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ActiveGameListViewModelTest {

    @get: Rule
    val rxTestRule = TrampolineTestExecutionRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var summonersDao: SummonerDao
    private lateinit var summonersRepo: SummonerRepo
    private lateinit var sut: ActiveGameListViewModel

    @Before
    fun setUp() {
        summonersDao = mockk()
        summonersRepo = mockk()
        every { summonersDao.getSummoners() }.answers {
            getListOfSummoners()
        }
        every {summonersDao.deleteSummoner(any())}.answers {  }
        summonerInMatch()
        sut = ActiveGameListViewModel(summonersDao, summonersRepo)
    }

    @Test
    fun `should correctly update summoners data when summoners are in game`() {
        val result = sut.summoners.value!!
        assertThat(result.size, `is`(getListOfSummoners().size))
        for (summoner in result) {
            assertThat(summoner.isLoading, `is`(false))
            assertNotNull(summoner.game)
        }
    }

    @Test
    fun `should correctly update summoners data when summoners are not in game`() {
        summonerNotInMatch()
        sut.refreshButtonClicked()
        val result = sut.summoners.value!!
        assertThat(result.size, `is`(getListOfSummoners().size))
        for (summoner in result) {
            assertThat(summoner.isLoading, `is`(false))
            assertNull(summoner.game)
        }
    }

    @Test
    fun `when delete summoner is called should correctly call dao`() {
        val summoner = Summoner("id", "name", Region.BR)
        sut.deleteSummoner(summoner)
        verify(exactly = 1) { summonersDao.deleteSummoner(summoner) }
    }


    private fun summonerInMatch() {
        every { summonersRepo.getSummonerActiveMatch(any(), any()) }
            .answers {
                Result.Success(getSummonerMatchStatus(getParticipants()))
            }
    }

    private fun summonerNotInMatch() {
        every { summonersRepo.getSummonerActiveMatch(any(), any()) }
            .answers {
                Result.Failure(SummonerNotInMatchException())
            }
    }

}