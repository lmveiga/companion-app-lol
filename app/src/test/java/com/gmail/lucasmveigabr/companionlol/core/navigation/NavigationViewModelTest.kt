package com.gmail.lucasmveigabr.companionlol.core.navigation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gmail.lucasmveigabr.companionlol.data.db.dao.SummonerDao
import com.gmail.lucasmveigabr.companionlol.data.model.NavigationEvent
import com.gmail.lucasmveigabr.companionlol.testutil.TrampolineTestExecutionRule
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationViewModelTest {

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    @get:Rule
    val rxRule = TrampolineTestExecutionRule()

    private lateinit var summonerDao: SummonerDao
    private lateinit var sut: NavigationViewModel

    @Before
    fun setUp() {
        summonerDao = mockk()
        sut = NavigationViewModel(summonerDao)
    }

    @Test
    fun `when no fragments are attached and there aren't any summoners in database should navigate to sign up fragment`() {
        every { summonerDao.getSummonerCount() }.answers { 0 }
        sut.noFragmentsAttached()
        assertThat(
            sut.navigation.value!!,
            instanceOf(NavigationEvent.SummonerSignUpNavigation::class.java)
        )
    }

    @Test
    fun `when no fragments are attached and there are summoners in database should navigate to games list fragment`() {
        every { summonerDao.getSummonerCount() }.answers { 1 }
        sut.noFragmentsAttached()
        assertThat(
            sut.navigation.value!!,
            instanceOf(NavigationEvent.ActiveGameListNavigation::class.java)
        )
    }

    @Test
    fun `when set navigation is called should correctly change livedata value`() {
        sut.setNavigation(NavigationEvent.ActiveGameListNavigation)
        assertThat(
            sut.navigation.value!!,
            instanceOf(NavigationEvent.ActiveGameListNavigation::class.java)
        )
    }

}