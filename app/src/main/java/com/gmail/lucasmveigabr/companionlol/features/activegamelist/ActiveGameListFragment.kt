package com.gmail.lucasmveigabr.companionlol.features.activegamelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.lucasmveigabr.companionlol.R
import com.gmail.lucasmveigabr.companionlol.app.App
import com.gmail.lucasmveigabr.companionlol.core.navigation.NavigationViewModel
import com.gmail.lucasmveigabr.companionlol.data.model.NavigationEvent
import com.gmail.lucasmveigabr.companionlol.data.model.Summoner
import com.gmail.lucasmveigabr.companionlol.databinding.FragmentActiveGameListBinding
import com.gmail.lucasmveigabr.companionlol.util.showLongSnackBar
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class ActiveGameListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ActiveGameListViewModel
    private lateinit var navigationViewModel: NavigationViewModel
    private lateinit var adapter: ActiveGameListAdapter
    private lateinit var binding: FragmentActiveGameListBinding

    private var refreshLastClick: Long = 0
    private var registerNewSummonerLastClick: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentActiveGameListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupRecyclerView()
        setupViewListeners()
    }

    private fun setupViewListeners() {
        binding.registerNewSummonerButton.setOnClickListener {
            if (System.currentTimeMillis() - registerNewSummonerLastClick >= 6000) {
                registerNewSummonerLastClick = System.currentTimeMillis()
                navigationViewModel.setNavigation(NavigationEvent.SummonerSignUpNavigation)
            }
        }
        binding.refreshButton.setOnClickListener {
            if (System.currentTimeMillis() - refreshLastClick >= 7000) {
                refreshLastClick = System.currentTimeMillis()
                viewModel.refreshButtonClicked()
            }
        }
    }

    private fun setupRecyclerView() {
        binding.summonersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.summonersRecyclerView.adapter = adapter
    }

    private fun setupAdapter() {
        adapter = ActiveGameListAdapter(requireContext(), {
            if (it?.game != null) {
                navigationViewModel.setNavigation(NavigationEvent.ActiveGameNavigation(it))
            } else {
                view?.showLongSnackBar(R.string.cant_select_summoner_not_in_match)
            }
        }, {
            deleteSummonerAlertDialog(it.summoner)
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadViewModel()
        subscribeToData()
    }

    private fun loadViewModel() {
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(ActiveGameListViewModel::class.java)
        navigationViewModel =
            ViewModelProvider(
                requireActivity(),
                viewModelFactory
            ).get(NavigationViewModel::class.java)
    }

    private fun subscribeToData() {
        viewModel.summoners.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    private fun deleteSummonerAlertDialog(summoner: Summoner) {
        val activity = activity ?: return
        AlertDialog.Builder(activity)
            .setMessage(R.string.delete_summoner)
            .setPositiveButton(android.R.string.yes) { _, _ ->
                viewModel.deleteSummoner(summoner)
            }
            .setNeutralButton(android.R.string.no, null)
            .create().show()
    }

}