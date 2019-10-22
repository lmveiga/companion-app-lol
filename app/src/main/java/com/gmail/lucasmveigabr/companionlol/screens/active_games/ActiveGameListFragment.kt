package com.gmail.lucasmveigabr.companionlol.screens.active_games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.lucasmveigabr.companionlol.NavigationViewModel
import com.gmail.lucasmveigabr.companionlol.R
import com.gmail.lucasmveigabr.companionlol.model.NavigationEvent
import com.gmail.lucasmveigabr.companionlol.model.SummonerInGame
import kotlinx.android.synthetic.main.active_game_list_fragment.*

class ActiveGameListFragment: Fragment() {

    private lateinit var viewModel: ActiveGameListViewModel
    private lateinit var navigationViewModel: NavigationViewModel
    private lateinit var adapter: ActiveGameListAdapter

    private var refreshLastClick: Long = 0
    private var registerNewSummonerLastClick: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.active_game_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ActiveGameListAdapter(requireContext())
        summoners_recycler_view.layoutManager = LinearLayoutManager(requireContext())
        summoners_recycler_view.adapter = adapter
        register_new_summoner_button.setOnClickListener {
            if (System.currentTimeMillis() - registerNewSummonerLastClick >= 6000) {
                registerNewSummonerLastClick = System.currentTimeMillis()
                navigationViewModel.setNavigation(NavigationEvent.SummonerSignupNavigation(false))
            }
        }
        refresh_button.setOnClickListener {
            if (System.currentTimeMillis() - refreshLastClick >= 7000) {
                refreshLastClick = System.currentTimeMillis()
                val summoners = adapter.getSummoners()
                for (summoner in summoners) {
                    viewModel.getObservableForSummoner(summoner.summoner)
                        .observe(viewLifecycleOwner, Observer { updatedSummoner ->
                            adapter.updateSummoner(updatedSummoner)
                        })
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ActiveGameListViewModel::class.java)
        navigationViewModel = ViewModelProvider(requireActivity()).get(NavigationViewModel::class.java)
        viewModel.getSummoners().observe(viewLifecycleOwner, Observer {summoners ->
            adapter.setSummoners(summoners.map {
                SummonerInGame(true, it, null)
            }.toMutableList())
            for (summoner in summoners){
                viewModel.getObservableForSummoner(summoner).observe(viewLifecycleOwner, Observer { sig ->
                    adapter.updateSummoner(sig)
                })
            }
        })
    }

}