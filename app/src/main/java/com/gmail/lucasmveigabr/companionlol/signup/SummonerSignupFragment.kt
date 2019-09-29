package com.gmail.lucasmveigabr.companionlol.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gmail.lucasmveigabr.companionlol.R
import com.gmail.lucasmveigabr.companionlol.model.Region
import com.gmail.lucasmveigabr.companionlol.signup.SummonerSignupViewModel.AddSummonerResult.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.signup_summoner_fragment.*
import java.lang.RuntimeException

class SummonerSignupFragment : Fragment() {

    private lateinit var viewModel: SummonerSignupViewModel

    companion object {

        private const val ARG_FIRST_SUMMONER = "first_summoner"

        @JvmStatic
        fun newInstance(isFirstSummoner: Boolean): SummonerSignupFragment {
            val fragment = SummonerSignupFragment()
            val bundle = Bundle()
            bundle.putBoolean(ARG_FIRST_SUMMONER, isFirstSummoner)
            fragment.arguments = bundle
            return fragment
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.signup_summoner_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        region_spinner.adapter = ArrayAdapter<Region>(
            requireContext(),
            android.R.layout.simple_list_item_1, Region.values()
        )
        next_button.setOnClickListener {
            viewModel.addSummoner(
                summoner_name_edit_text.text.toString(),
                region_spinner.selectedItem as Region
            )
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SummonerSignupViewModel::class.java)
        viewModel.getSummonerResult().observe(viewLifecycleOwner, Observer { result ->
            val view = view
            if (view != null)
                when (result) {
                    SUCCESS -> Snackbar.make(
                        view,
                        "Summoner Adicionado com Sucesso",
                        Snackbar.LENGTH_LONG
                    ).show()
                    NOT_FOUND -> Snackbar.make(
                        view,
                        "Summoner não encontrado",
                        Snackbar.LENGTH_LONG
                    ).show()
                    NETWORK_ERROR -> Snackbar.make(
                        view,
                        "Erro ao buscar informações",
                        Snackbar.LENGTH_LONG
                    ).show()
                    else -> throw RuntimeException("NOT AVAILABLE")
                }
        })
    }


}