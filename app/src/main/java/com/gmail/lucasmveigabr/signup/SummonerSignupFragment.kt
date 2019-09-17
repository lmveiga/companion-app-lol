package com.gmail.lucasmveigabr.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gmail.lucasmveigabr.R

class SummonerSignupFragment : Fragment() {


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


}