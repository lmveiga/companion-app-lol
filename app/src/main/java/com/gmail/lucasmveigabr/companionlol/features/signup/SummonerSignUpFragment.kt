package com.gmail.lucasmveigabr.companionlol.features.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gmail.lucasmveigabr.companionlol.R
import com.gmail.lucasmveigabr.companionlol.app.App
import com.gmail.lucasmveigabr.companionlol.core.navigation.NavigationViewModel
import com.gmail.lucasmveigabr.companionlol.data.model.NavigationEvent
import com.gmail.lucasmveigabr.companionlol.data.model.Region
import com.gmail.lucasmveigabr.companionlol.databinding.FragmentSummonerSignupBinding
import com.gmail.lucasmveigabr.companionlol.features.signup.SummonerSignUpViewModel.AddSummonerResult.*
import com.gmail.lucasmveigabr.companionlol.util.showLongSnackBar
import javax.inject.Inject

class SummonerSignUpFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: SummonerSignUpViewModel
    private lateinit var navigationViewModel: NavigationViewModel
    private lateinit var binding: FragmentSummonerSignupBinding
    private var buttonLastClick: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSummonerSignupBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRegionSpinner()
        setupButtons()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadViewModels()
        subscribeToData()
    }

    private fun subscribeToData() {
        viewModel.addSummonerResult.observe(viewLifecycleOwner, Observer { result ->
            val view = view
            if (view != null && result != null)
                when (result) {
                    SUCCESS ->
                        navigationViewModel.setNavigation(NavigationEvent.ActiveGameListNavigation)
                    NOT_FOUND -> view.showLongSnackBar(R.string.summoner_not_found)
                    NETWORK_ERROR -> view.showLongSnackBar(R.string.network_error)
                }
        })
    }

    private fun loadViewModels() {
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(SummonerSignUpViewModel::class.java)
        navigationViewModel = ViewModelProvider(requireActivity(), viewModelFactory)
            .get(NavigationViewModel::class.java)
    }

    private fun setupRegionSpinner() {
        binding.regionSpinner.adapter = ArrayAdapter<Region>(
            requireContext(),
            android.R.layout.simple_list_item_1, Region.values()
        )
    }

    private fun setupButtons() {
        binding.nextButton.setOnClickListener {
            if (System.currentTimeMillis() - buttonLastClick >= 8000) {
                buttonLastClick = System.currentTimeMillis()
                viewModel.addSummoner(
                    binding.summonerNameEditText.text.toString(),
                    binding.regionSpinner.selectedItem as Region
                )
            }
        }
        binding.summonerListButton.setOnClickListener {
            navigationViewModel.setNavigation(NavigationEvent.ActiveGameListNavigation)
        }
    }


}