package com.gmail.lucasmveigabr.companionlol.screens.active_games

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.lucasmveigabr.companionlol.R
import com.gmail.lucasmveigabr.companionlol.model.SummonerInGame
import kotlinx.android.synthetic.main.summoners_active_game_adapter_holder.view.*
import java.util.*

class ActiveGameListAdapter(val context: Context): RecyclerView.Adapter<ActiveGameListAdapter.ActiveGameListHolder>() {

    private var summoners: MutableList<SummonerInGame> = Collections.emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveGameListHolder {
        val v =LayoutInflater.from(context).inflate(R.layout.summoners_active_game_adapter_holder, parent, false)
        return ActiveGameListHolder(v)
    }

    override fun getItemCount(): Int {
        return summoners.size
    }

    override fun onBindViewHolder(holder: ActiveGameListHolder, position: Int) {
        holder.bindHolder(summoners.get(position))
    }

    fun setSummoners(list: MutableList<SummonerInGame>){
        summoners = list
        notifyDataSetChanged()
    }

    fun updateSummoner(summoner: SummonerInGame){
        try {
            val index = summoners.indexOf(summoner)
            summoners[index] = summoner
            notifyItemChanged(index)
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    class ActiveGameListHolder(val view: View): RecyclerView.ViewHolder(view){


        fun bindHolder(item: SummonerInGame){
            view.summoner_name_text_view.text =
                "${item.summoner.region.toString()} - ${item.summoner.summonnerName}"
            view.game_active_image.visibility = if (item.game != null) View.VISIBLE else View.GONE
        }

    }
}