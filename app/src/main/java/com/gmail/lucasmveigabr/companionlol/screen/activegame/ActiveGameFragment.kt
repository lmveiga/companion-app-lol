package com.gmail.lucasmveigabr.companionlol.screen.activegame

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
import com.gmail.lucasmveigabr.companionlol.R
import com.gmail.lucasmveigabr.companionlol.core.navigation.NavigationViewModel
import com.gmail.lucasmveigabr.companionlol.model.ActiveGameChampionsAdapterState
import com.gmail.lucasmveigabr.companionlol.model.EnemySummoner
import com.gmail.lucasmveigabr.companionlol.model.SummonerInGame
import com.gmail.lucasmveigabr.companionlol.util.setVisible
import kotlinx.android.synthetic.main.fragment_active_game.*

private const val GAME_STATE = "game_state"
private const val ADAPTER_STATE = "adapter_state"

class ActiveGameFragment : Fragment() {

    private lateinit var viewModel: ActiveGameViewModel
    private lateinit var navigationViewModel: NavigationViewModel
    private lateinit var currentGameViewModel: CurrentGameViewModel

    private var game: SummonerInGame? = null

    private var adapter: ActiveGameChampionsAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_active_game, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()
        loadViewModel()
        if (savedInstanceState != null) {
            loadSavedState(savedInstanceState)
        }
        subscribeToData()
    }

    private fun setupRecyclerView() {
        champions_recycler_view.layoutManager = LinearLayoutManager(requireContext())
        val touchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            0
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                adapter?.championMoved(viewHolder.adapterPosition, target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
        })
        touchHelper.attachToRecyclerView(champions_recycler_view)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        game?.let { game ->
            outState.putParcelable(GAME_STATE, game)
        }
        val enemies = adapter?.enemies
        if (enemies != null) {
            outState.putParcelable(ADAPTER_STATE, ActiveGameChampionsAdapterState(enemies))
        }
        super.onSaveInstanceState(outState)
    }

    private fun subscribeToData() {
        currentGameViewModel.currentGame.observe(viewLifecycleOwner, Observer {
            this.game = it
            viewModel.setCurrentGame(it)
        })
        viewModel.enemies.observe(viewLifecycleOwner, Observer {
            setupEnemyChampions(it)
        })
    }

    private fun loadSavedState(savedInstanceState: Bundle) {
        val game = savedInstanceState.getParcelable<SummonerInGame>(GAME_STATE)
        val enemies =
            savedInstanceState.getParcelable<ActiveGameChampionsAdapterState>(ADAPTER_STATE)
        this.game = game
        if (enemies == null && game != null) {
            viewModel.setCurrentGame(game)
        }
        if (enemies != null) {
            setupEnemyChampions(enemies.enemies)
        }
    }

    private fun loadViewModel() {
        viewModel = ViewModelProvider(this)[ActiveGameViewModel::class.java]
        navigationViewModel = ViewModelProvider(this)[NavigationViewModel::class.java]
        currentGameViewModel =
            ViewModelProvider(requireActivity())[CurrentGameViewModel::class.java]
    }

    private fun setupEnemyChampions(it: List<EnemySummoner>) {
        progress_bar.setVisible(false)
        adapter = ActiveGameChampionsAdapter(requireContext(), it.toMutableList())
        champions_recycler_view.adapter = adapter
    }


}