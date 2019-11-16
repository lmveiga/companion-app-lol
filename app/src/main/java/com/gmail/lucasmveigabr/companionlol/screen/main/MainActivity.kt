package com.gmail.lucasmveigabr.companionlol.screen.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gmail.lucasmveigabr.companionlol.core.navigation.NavigationViewModel
import com.gmail.lucasmveigabr.companionlol.R
import com.gmail.lucasmveigabr.companionlol.app.App
import com.gmail.lucasmveigabr.companionlol.model.NavigationEvent
import com.gmail.lucasmveigabr.companionlol.screen.activegame.ActiveGameFragment
import com.gmail.lucasmveigabr.companionlol.screen.activegamelist.ActiveGameListFragment
import com.gmail.lucasmveigabr.companionlol.screen.signup.SummonerSignupFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.appComponent?.inject(this)
        setupViewModel()
    }

    private fun setupViewModel() {
        val viewModel = ViewModelProvider(this, viewModelFactory).get(NavigationViewModel::class.java)
        viewModel.navigation.observe(this, Observer { navigation ->
            navigate(navigation)
        })

        if (supportFragmentManager.findFragmentById(R.id.container) == null) {
            viewModel.noFragmentsAttached()
        }
    }

    private fun navigate(navigation: NavigationEvent) {
        val replaceFragment =
            when (navigation) {
                is NavigationEvent.SummonerSignUpNavigation -> SummonerSignupFragment()
                is NavigationEvent.ActiveGameListNavigation -> ActiveGameListFragment()
                is NavigationEvent.ActiveGameNavigation -> ActiveGameFragment()
            }
        supportFragmentManager.beginTransaction().replace(R.id.container, replaceFragment)
            .commit()
    }

    override fun onBackPressed() {
        when (supportFragmentManager.findFragmentById(R.id.container)) {
            is ActiveGameFragment -> {
                navigate(NavigationEvent.ActiveGameListNavigation)
            }
            else -> {
                super.onBackPressed()
            }
        }
    }


}
