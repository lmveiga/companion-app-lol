package com.gmail.lucasmveigabr.companionlol.features.activegame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gmail.lucasmveigabr.companionlol.R
import com.gmail.lucasmveigabr.companionlol.app.App
import com.gmail.lucasmveigabr.companionlol.core.navigation.NavigationViewModel
import com.gmail.lucasmveigabr.companionlol.data.model.ActiveGameChampionsAdapterState
import com.gmail.lucasmveigabr.companionlol.data.model.EnemySummoner
import com.gmail.lucasmveigabr.companionlol.data.model.SummonerInGame
import com.gmail.lucasmveigabr.companionlol.databinding.FragmentActiveGameBinding
import com.gmail.lucasmveigabr.companionlol.util.Endpoints
import com.gmail.lucasmveigabr.companionlol.util.setVisible
import javax.inject.Inject

private const val GAME_STATE = "game_state"
private const val ADAPTER_STATE = "adapter_state"
private const val SUMMONER_IN_GAME_ARG = "summoner_in_game_arg"

class ActiveGameFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ActiveGameViewModel
    private lateinit var navigationViewModel: NavigationViewModel

    private lateinit var adapter: ActiveGameChampionsAdapter
    private lateinit var binding: FragmentActiveGameBinding

    private var game: SummonerInGame? = null
        set(value) {
            field = value
            if (value != null) {
                val summoner =
                    value.game?.participants?.firstOrNull { it.summonerId == value.summoner.encryptedId }
                if (summoner != null) {
                    Glide.with(requireActivity())
                        .load(Endpoints.profileIcon(summoner.profileIconId))
                        .into(binding.currentSummonerImageView)
                    binding.summonerNameTextView.text = summoner.summonerName
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentActiveGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()
        loadViewModel()
        if (savedInstanceState != null) {
            loadSavedState(savedInstanceState)
        } else {
            loadArguments()
        }
        subscribeToData()
    }

    private fun loadArguments() {
        arguments?.let { arguments ->
            val game = arguments.getParcelable<SummonerInGame>(SUMMONER_IN_GAME_ARG)
            if (game != null) {
                this.game = game
                viewModel.setCurrentGame(game)
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = ActiveGameChampionsAdapter(requireActivity())
        binding.championsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.championsRecyclerView.adapter = adapter
        val touchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            0
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                adapter.championMoved(viewHolder.adapterPosition, target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
        })
        touchHelper.attachToRecyclerView(binding.championsRecyclerView)
    }

    private fun subscribeToData() {
        viewModel.enemies.observe(viewLifecycleOwner, Observer {
            setupEnemyChampions(it)
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        game?.let { game ->
            outState.putParcelable(GAME_STATE, game)
        }
        val enemies = adapter.enemies
        outState.putParcelable(ADAPTER_STATE, ActiveGameChampionsAdapterState(enemies))
        super.onSaveInstanceState(outState)
    }

    private fun loadSavedState(savedInstanceState: Bundle) {
        val game = savedInstanceState.getParcelable<SummonerInGame>(GAME_STATE)
        val enemies =
            savedInstanceState.getParcelable<ActiveGameChampionsAdapterState>(ADAPTER_STATE)
        this.game = game
        if (enemies != null) {
            setupEnemyChampions(enemies.enemies)
        }
    }

    private fun loadViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[ActiveGameViewModel::class.java]
        navigationViewModel =
            ViewModelProvider(this, viewModelFactory)[NavigationViewModel::class.java]
    }

    private fun setupEnemyChampions(it: List<EnemySummoner>) {
        setProgressLayout(false)
        adapter.enemies = it.toMutableList()
    }

    private fun setProgressLayout(visible: Boolean) {
        binding.progressBar.setVisible(visible)
        binding.currentSummonerCard.setVisible(!visible)
        binding.championsRecyclerView.setVisible(!visible)
    }

    companion object {
        @JvmStatic
        fun newInstance(summonerInGame: SummonerInGame): ActiveGameFragment {
            val fragment = ActiveGameFragment()
            val args = Bundle()
            args.putParcelable(SUMMONER_IN_GAME_ARG, summonerInGame)
            fragment.arguments = args
            return fragment
        }
    }

}