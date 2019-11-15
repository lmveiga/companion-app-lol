package com.gmail.lucasmveigabr.companionlol.screen.activegamelist

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gmail.lucasmveigabr.companionlol.R
import com.gmail.lucasmveigabr.companionlol.model.SummonerInGame
import com.gmail.lucasmveigabr.companionlol.util.setVisible
import kotlinx.android.synthetic.main.holder_summoners_active_game.view.*
import java.util.*

class ActiveGameListAdapter(val context: Context, private val onClick: ((SummonerInGame?) -> Unit)) :
    ListAdapter<SummonerInGame, ActiveGameListAdapter.ActiveGameListHolder>(SummonerInGame.diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveGameListHolder =
        ActiveGameListHolder(LayoutInflater.from(context)
            .inflate(R.layout.holder_summoners_active_game, parent, false), onClick)

    override fun onBindViewHolder(holder: ActiveGameListHolder, position: Int) {
        holder.bindHolder(getItem(position))
    }

    inner class ActiveGameListHolder(
        private val view: View,
        onClick: (SummonerInGame?) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener {
                onClick(getItem(adapterPosition))
            }
        }

        @SuppressLint("SetTextI18n")
        fun bindHolder(item: SummonerInGame) {
            with(view) {
                summoner_name_text_view.text =
                    "${item.summoner.region} - ${item.summoner.summonnerName}"
                game_active_image.setVisible(item.game != null)
                progress_bar.setVisible(item.isLoading)
            }
        }


    }
}