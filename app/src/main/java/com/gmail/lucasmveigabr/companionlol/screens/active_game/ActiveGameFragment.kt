package com.gmail.lucasmveigabr.companionlol.screens.active_game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gmail.lucasmveigabr.companionlol.CurrentGameViewModel
import com.gmail.lucasmveigabr.companionlol.NavigationViewModel
import com.gmail.lucasmveigabr.companionlol.R

class ActiveGameFragment: Fragment() {



    private lateinit var viewModel: ActiveGameViewModel
    private lateinit var navigationViewModel: NavigationViewModel
    private lateinit var currentGameViewModel: CurrentGameViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_active_game, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[ActiveGameViewModel::class.java]
        navigationViewModel = ViewModelProvider(this)[NavigationViewModel::class.java]
        currentGameViewModel = ViewModelProvider(this)[CurrentGameViewModel::class.java]
        currentGameViewModel.getCurrentGame().observe(viewLifecycleOwner, Observer {

        })
    }

}