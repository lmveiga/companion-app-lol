package com.gmail.lucasmveigabr.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.gmail.lucasmveigabr.App
import com.gmail.lucasmveigabr.R
import com.gmail.lucasmveigabr.model.Region
import com.gmail.lucasmveigabr.model.Result
import com.gmail.lucasmveigabr.model.Result.Failure
import com.gmail.lucasmveigabr.model.Result.Success
import com.gmail.lucasmveigabr.model.SummonerResponse
import com.gmail.lucasmveigabr.networking.retrofit.SummonerApi
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.signup_summoner_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

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
        region_spinner.adapter = ArrayAdapter<Region>(requireContext(),
            android.R.layout.simple_list_item_1, Region.values())
        next_button.setOnClickListener {

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SummonerSignupViewModel::class.java)
        viewModel.getSummonerResult().observe(viewLifecycleOwner, Observer { result ->
            val view = view
            when (result){
                is Success -> {
                    if (view != null)
                        Snackbar.make(view, "Summoner válido!", Snackbar.LENGTH_LONG).show()
                }
                is Failure -> {
                    if (view != null)
                        Snackbar.make(view, result.error.message.toString(), Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }


}