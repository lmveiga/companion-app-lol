package com.gmail.lucasmveigabr.companionlol.screens.active_game_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.lucasmveigabr.companionlol.R
import com.gmail.lucasmveigabr.companionlol.model.SummonerInGame
import kotlinx.android.synthetic.main.summoners_active_game_adapter_holder.view.*
import java.util.*

class ActiveGameListAdapter(val context: Context, val onClick: ((SummonerInGame?) -> Unit)): RecyclerView.Adapter<ActiveGameListAdapter.ActiveGameListHolder>() {

    private var summoners: MutableList<SummonerInGame> = Collections.emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveGameListHolder {
        val v =LayoutInflater.from(context).inflate(R.layout.summoners_active_game_adapter_holder, parent, false)
        return ActiveGameListHolder(v, onClick)
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

    fun getSummoners() = summoners

    fun updateSummoner(summoner: SummonerInGame){
        try {
            val index = summoners.indexOf(summoner)
            summoners[index] = summoner
            notifyItemChanged(index)
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    inner class ActiveGameListHolder(
        val view: View,
        onClick: (SummonerInGame?) -> Unit
    ): RecyclerView.ViewHolder(view){

        init {
            view.setOnClickListener {
                onClick(summoners[adapterPosition])
            }
        }

        fun bindHolder(item: SummonerInGame){
            view.summoner_name_text_view.text =
                "${item.summoner.region.toString()} - ${item.summoner.summonnerName}"
            view.game_active_image.visibility = if (item.game != null) View.VISIBLE else View.GONE
        }


    }
}