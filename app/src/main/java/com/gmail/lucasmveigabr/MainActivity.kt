package com.gmail.lucasmveigabr

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gmail.lucasmveigabr.signup.SummonerSignupFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.container, SummonerSignupFragment.newInstance(true))
            .commit()
    }
}
