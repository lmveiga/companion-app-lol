package com.gmail.lucasmveigabr.companionlol.features.activegamelist

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gmail.lucasmveigabr.companionlol.R
import com.gmail.lucasmveigabr.companionlol.data.model.SummonerInGame
import com.gmail.lucasmveigabr.companionlol.util.setVisible

class ActiveGameListAdapter(
    val context: Context,
    private val onClick: ((SummonerInGame?) -> Unit),
    private val onLongClick: ((SummonerInGame) -> Unit)
) : ListAdapter<SummonerInGame, ActiveGameListAdapter.ActiveGameListHolder>(SummonerInGame.diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveGameListHolder =
        ActiveGameListHolder(
            LayoutInflater.from(context).inflate(
                R.layout.holder_summoners_active_game, parent, false), onClick, onLongClick)

    override fun onBindViewHolder(holder: ActiveGameListHolder, position: Int) {
        holder.bindHolder(getItem(position))
    }

    inner class ActiveGameListHolder(
        private val view: View,
        onClick: (SummonerInGame?) -> Unit,
        onLongClick: (SummonerInGame) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        init {
            view.setOnLongClickListener {
                onLongClick(getItem(adapterPosition))
                true
             }
            view.setOnClickListener {
                onClick(getItem(adapterPosition))
            }
        }

        @SuppressLint("SetTextI18n")
        fun bindHolder(item: SummonerInGame) {
            with(view) {
                findViewById<TextView>(R.id.summoner_name_text_view).text =
                    "${item.summoner.region} - ${item.summoner.summonnerName}"
                findViewById<ImageView>(R.id.game_active_image).setVisible(item.game != null)
                findViewById<ProgressBar>(R.id.progress_bar).setVisible(item.isLoading)
            }
        }


    }
}