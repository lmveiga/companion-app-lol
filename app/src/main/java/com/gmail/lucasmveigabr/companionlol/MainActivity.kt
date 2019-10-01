package com.gmail.lucasmveigabr.companionlol

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gmail.lucasmveigabr.companionlol.model.NavigationEvent
import com.gmail.lucasmveigabr.companionlol.signup.SummonerSignupFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getNavigation().observe(this, Observer {navigation ->
            when (navigation) {
                is NavigationEvent.SummonerSignupNavigation -> TODO()
                is NavigationEvent.ActiveGamesNavigation -> TODO()
            }
        })
        supportFragmentManager.beginTransaction()
            .add(R.id.container, SummonerSignupFragment.newInstance(true))
            .commit()
    }
}
