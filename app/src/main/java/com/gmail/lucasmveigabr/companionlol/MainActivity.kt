package com.gmail.lucasmveigabr.companionlol

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gmail.lucasmveigabr.companionlol.model.NavigationEvent
import com.gmail.lucasmveigabr.companionlol.screens.active_games.ActiveGameListFragment
import com.gmail.lucasmveigabr.companionlol.screens.signup.SummonerSignupFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModel = ViewModelProvider(this).get(NavigationViewModel::class.java)
        viewModel.getNavigation().observe(this, Observer {navigation ->
            navigate(navigation)
        })
        if (supportFragmentManager.findFragmentById(R.id.container) == null){
            viewModel.noFragmentsAttached()
        }
    }

    fun navigate(navigation: NavigationEvent){
        //val currentFragment = supportFragmentManager.findFragmentById(R.id.container)
        val replaceFragment =
        when (navigation) {
            is NavigationEvent.SummonerSignupNavigation -> SummonerSignupFragment()
            is NavigationEvent.ActiveGamesNavigation -> ActiveGameListFragment()
        }
        supportFragmentManager.beginTransaction().replace(R.id.container, replaceFragment)
            .commit()
    }
}
