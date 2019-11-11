package com.gmail.lucasmveigabr.companionlol.screens.active_game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.lucasmveigabr.companionlol.CurrentGameViewModel
import com.gmail.lucasmveigabr.companionlol.NavigationViewModel
import com.gmail.lucasmveigabr.companionlol.R
import com.gmail.lucasmveigabr.companionlol.model.SummonerInGame
import kotlinx.android.synthetic.main.fragment_active_game.*

class ActiveGameFragment: Fragment() {

    companion object {
        private const val  GAME_STATE = "game_state"
    }

    private lateinit var viewModel: ActiveGameViewModel
    private lateinit var navigationViewModel: NavigationViewModel
    private lateinit var currentGameViewModel: CurrentGameViewModel
    private var game: SummonerInGame? = null


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
        currentGameViewModel = ViewModelProvider(requireActivity())[CurrentGameViewModel::class.java]
        if (savedInstanceState != null){
            val game = SummonerInGame.fromJson(savedInstanceState.getString(GAME_STATE)?:"")
            if (game != null) {
                this.game = game
                viewModel.setCurrentGame(game)
            }
        }
        //TODO(ACTIVITY RECREATION LOSES COOLDOWNS)
        currentGameViewModel.getCurrentGame().observe(viewLifecycleOwner, Observer {
            this.game = it
            viewModel.setCurrentGame(it)
        })
        viewModel.getEnemies().observe(viewLifecycleOwner, Observer {
            val adapter = ActiveGameChampionsAdapter(requireContext(), it){

            }
            champions_recycler_view.layoutManager = LinearLayoutManager(requireContext())
            champions_recycler_view.adapter = adapter
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        game?.let {game ->
            outState.putString(GAME_STATE, game.toJson())
        }
        super.onSaveInstanceState(outState)
    }


}