package com.gmail.lucasmveigabr.companionlol.screen.activegamelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.lucasmveigabr.companionlol.screen.activegame.CurrentGameViewModel
import com.gmail.lucasmveigabr.companionlol.core.navigation.NavigationViewModel
import com.gmail.lucasmveigabr.companionlol.R
import com.gmail.lucasmveigabr.companionlol.app.App
import com.gmail.lucasmveigabr.companionlol.model.NavigationEvent
import kotlinx.android.synthetic.main.fragment_active_game_list.*
import javax.inject.Inject

class ActiveGameListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ActiveGameListViewModel
    private lateinit var navigationViewModel: NavigationViewModel
    private lateinit var currentGameViewModel: CurrentGameViewModel
    private lateinit var adapter: ActiveGameListAdapter

    private var refreshLastClick: Long = 0
    private var registerNewSummonerLastClick: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_active_game_list, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupRecyclerView()
        setupViewListeners()
    }

    private fun setupViewListeners() {
        register_new_summoner_button.setOnClickListener {
            if (System.currentTimeMillis() - registerNewSummonerLastClick >= 6000) {
                registerNewSummonerLastClick = System.currentTimeMillis()
                navigationViewModel.setNavigation(NavigationEvent.SummonerSignUpNavigation)
            }
        }
        refresh_button.setOnClickListener {
            if (System.currentTimeMillis() - refreshLastClick >= 7000) {
                refreshLastClick = System.currentTimeMillis()
                viewModel.refreshButtonClicked()
            }
        }
    }

    private fun setupRecyclerView() {
        summoners_recycler_view.layoutManager = LinearLayoutManager(requireContext())
        summoners_recycler_view.adapter = adapter
    }

    private fun setupAdapter() {
        adapter = ActiveGameListAdapter(requireContext()) {
            if (it?.game != null) {
                currentGameViewModel.setCurrentGame(it)
                navigationViewModel.setNavigation(NavigationEvent.ActiveGameNavigation)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadViewModel()
        subscribeToData()
    }

    private fun loadViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(ActiveGameListViewModel::class.java)
        navigationViewModel =
            ViewModelProvider(requireActivity()).get(NavigationViewModel::class.java)
        currentGameViewModel =
            ViewModelProvider(requireActivity())[CurrentGameViewModel::class.java]
    }

    private fun subscribeToData() {
        viewModel.summoners.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

}