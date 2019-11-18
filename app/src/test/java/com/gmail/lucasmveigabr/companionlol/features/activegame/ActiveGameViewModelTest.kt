package com.gmail.lucasmveigabr.companionlol.features.activegame

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gmail.lucasmveigabr.companionlol.data.model.Region
import com.gmail.lucasmveigabr.companionlol.data.model.Result
import com.gmail.lucasmveigabr.companionlol.data.model.Summoner
import com.gmail.lucasmveigabr.companionlol.data.model.SummonerInGame
import com.gmail.lucasmveigabr.companionlol.data.repository.ChampionRepo
import com.gmail.lucasmveigabr.companionlol.data.repository.SpellsRepo
import com.gmail.lucasmveigabr.companionlol.testutil.TrampolineTestExecutionRule
import com.gmail.lucasmveigabr.companionlol.testutil.data.GameData.getChampionSchema
import com.gmail.lucasmveigabr.companionlol.testutil.data.GameData.getGameData
import com.gmail.lucasmveigabr.companionlol.testutil.data.GameData.getGameDataWithCdrPerk
import com.gmail.lucasmveigabr.companionlol.testutil.data.GameData.getSpellSchema
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ActiveGameViewModelTest {

    @get: Rule
    val rxTestRule = TrampolineTestExecutionRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var championRepo: ChampionRepo
    private lateinit var spellsRepo: SpellsRepo
    private lateinit var sut: ActiveGameViewModel


    @Before
    fun setUp() {
        championRepo = mockk()
        spellsRepo = mockk()
        sut = ActiveGameViewModel(championRepo, spellsRepo)
        every { championRepo.getChampion(any()) }.answers {
            Result.Success(getChampionSchema())
        }
        every { spellsRepo.getSpell(any()) }.answers {
            Result.Success(getSpellSchema())
        }
    }

    @Test(expected = Exception::class)
    fun `when current game is set with null game should throw exception`() {
        sut.setCurrentGame(SummonerInGame(false, Summoner("id", "name", Region.BR), null))
    }

    @Test
    fun `when game is set should correctly map enemies`() {
        val game = getGameData()
        sut.setCurrentGame(game)
        val result = sut.enemies.value
        for (participant in result!!) {
            assertThat(participant.summonerName, `is`("enemy"))
        }
    }

    @Test
    fun `when participant has cdr rune should reduce summoner cd by 5 percent`() {
        val game = getGameDataWithCdrPerk()
        sut.setCurrentGame(game)
        val result = sut.enemies.value
        for (participant in result!!) {
            assertTrue(participant.spell1!!.cooldown == 95.0)
            assertTrue(participant.spell2!!.cooldown == 95.0)
        }
    }
}