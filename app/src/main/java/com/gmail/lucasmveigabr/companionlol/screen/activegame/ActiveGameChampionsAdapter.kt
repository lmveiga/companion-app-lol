package com.gmail.lucasmveigabr.companionlol.screen.activegame

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gmail.lucasmveigabr.companionlol.R
import com.gmail.lucasmveigabr.companionlol.model.EnemySummoner
import kotlinx.android.synthetic.main.holder_active_game_champions.view.*
import java.util.*

class ActiveGameChampionsAdapter(
    val context: Context,
    val enemies: MutableList<EnemySummoner>,
    private val onClick: ((EnemySummoner) -> Unit) = {}
) :
    RecyclerView.Adapter<ActiveGameChampionsAdapter.ActiveGameChampionsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveGameChampionsHolder {
        val v = LayoutInflater.from(context)
            .inflate(R.layout.holder_active_game_champions, parent, false)
        return ActiveGameChampionsHolder(v, onClick)
    }

    override fun getItemCount(): Int {
        return enemies.size
    }

    override fun onBindViewHolder(holder: ActiveGameChampionsHolder, position: Int) {
        holder.bind(enemies[position])
    }

    fun championMoved(initialPos: Int, finalPos: Int){
        Collections.swap(enemies, initialPos, finalPos)
        notifyItemMoved(initialPos, finalPos)
    }


    inner class ActiveGameChampionsHolder(
        private val view: View,
        onClick: (EnemySummoner) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener { onClick(enemies[adapterPosition]) }
        }

        fun bind(enemy: EnemySummoner) {
            Glide.with(context)
                .load(enemy.championIconUrl).into(view.champion_icon)
            if (enemy.spell1 != null) {
                view.spell1.setSpell(enemy.spell1)
            }
            if (enemy.spell2 != null) {
                view.spell2.setSpell(enemy.spell2)
            }
            view.summoner_name_text_view.text = enemy.summonerName
        }
    }
}