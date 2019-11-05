package com.gmail.lucasmveigabr.companionlol.screens.active_game

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gmail.lucasmveigabr.companionlol.R
import com.gmail.lucasmveigabr.companionlol.model.EnemySummoner
import com.gmail.lucasmveigabr.companionlol.util.Endpoints
import com.gmail.lucasmveigabr.companionlol.util.setVisible
import kotlinx.android.synthetic.main.holder_active_game_champions.view.*
import java.util.*

class ActiveGameChampionsAdapter(
    val context: Context,
    val enemies: List<EnemySummoner>,
    val onClick: ((EnemySummoner) -> Unit)
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
                Glide.with(context)
                    .load(Endpoints.spellIcon(enemy.spell1.image.full))
                    .into(view.spell1_image)
                view.spell1_image.setOnClickListener {
                    if (enemy.firstSpellCd <= 0) {
                        view.spell1_darkoverlay.setVisible(true)
                        enemy.firstSpellCd = enemy.spell1.cooldown.firstOrNull() ?: 0.0
                        //TODO("DO NOT LET TWO TIMERS GO OFF")
                        val handler = Handler()
                        val runnable = object : Runnable {
                            override fun run() {
                                enemy.firstSpellCd -= 1
                                view.spell1_cd.text = enemy.firstSpellCd.toString()
                                if (enemy.firstSpellCd > 0)
                                    handler.postDelayed(this, 1000)
                                else {
                                    view.spell1_cd.text = ""
                                    view.spell1_darkoverlay.setVisible(false)
                                }
                            }
                        }
                        handler.postDelayed(runnable, 0)
                    }
                }
            }
            if (enemy.spell2 != null) {
                Glide.with(context)
                    .load(Endpoints.spellIcon(enemy.spell2.image.full))
                    .into(view.spell2_image)
                view.spell2_image.setOnClickListener {
                    if (enemy.secondSpellCd <= 0) {
                        view.spell2_darkoverlay.setVisible(true)
                        enemy.secondSpellCd = enemy.spell2.cooldown.firstOrNull() ?: 0.0
                        //TODO("DO NOT LET TWO TIMERS GO OFF")
                        val handler = Handler()
                        val runnable = object : Runnable {
                            override fun run() {
                                enemy.secondSpellCd -= 1
                                view.spell2_cd.text = enemy.secondSpellCd.toString()
                                if (enemy.secondSpellCd > 0)
                                    handler.postDelayed(this, 1000)
                                else {
                                    view.spell2_cd.text = ""
                                    view.spell2_darkoverlay.setVisible(false)
                                }
                            }
                        }
                        handler.postDelayed(runnable, 0)
                    }
                }
            }
            view.summoner_name_text_view.text = enemy.summonerName

        }
    }
}