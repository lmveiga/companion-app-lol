package com.gmail.lucasmveigabr.companionlol.features.activegame

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gmail.lucasmveigabr.companionlol.R
import com.gmail.lucasmveigabr.companionlol.data.model.EnemySummoner
import kotlinx.android.synthetic.main.holder_active_game_champions.view.*
import java.util.*

class ActiveGameChampionsAdapter(val context: Context) :
    RecyclerView.Adapter<ActiveGameChampionsAdapter.ActiveGameChampionsHolder>() {

    var enemies: MutableList<EnemySummoner> = Collections.emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveGameChampionsHolder =
        ActiveGameChampionsHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.holder_active_game_champions, parent, false)
        )

    override fun getItemCount(): Int {
        return enemies.size
    }

    override fun onBindViewHolder(holder: ActiveGameChampionsHolder, position: Int) {
        holder.bind(enemies[position])
    }

    fun championMoved(initialPos: Int, finalPos: Int) {
        Collections.swap(enemies, initialPos, finalPos)
        notifyItemMoved(initialPos, finalPos)
    }

    inner class ActiveGameChampionsHolder(private val view: View) : RecyclerView.ViewHolder(view) {

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