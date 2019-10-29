package com.gmail.lucasmveigabr.companionlol.screens.active_game

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.lucasmveigabr.companionlol.R
import com.gmail.lucasmveigabr.companionlol.model.EnemySummoner

class ActiveGameChampionsAdapter(val context: Context, val enemies: List<EnemySummoner>, val onClick: ((EnemySummoner) -> Unit)):
    RecyclerView.Adapter<ActiveGameChampionsAdapter.ActiveGameChampionsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveGameChampionsHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.holder_active_game_champions, parent, false)
        return ActiveGameChampionsHolder(v, onClick)
    }

    override fun getItemCount(): Int {
        return enemies.size
    }

    override fun onBindViewHolder(holder: ActiveGameChampionsHolder, position: Int) {
        holder.bind(enemies[position])
    }


    inner class ActiveGameChampionsHolder(
        val view: View,
        onClick: (EnemySummoner) -> Unit
    ): RecyclerView.ViewHolder(view){

        init {
            view.setOnClickListener { onClick(enemies[adapterPosition]) }
        }

        fun bind(enemy: EnemySummoner){

        }
    }
}