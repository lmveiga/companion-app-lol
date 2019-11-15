package com.gmail.lucasmveigabr.companionlol.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.GsonBuilder
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SummonerInGame(
    val isLoading: Boolean,
    val summoner: Summoner,
    val game: SummonerMatchStatus?
): Parcelable {

    override fun equals(other: Any?): Boolean {
        if (other !is SummonerInGame) return false
        return summoner.encryptedId == other.summoner.encryptedId
    }

    override fun hashCode(): Int {
        return summoner.encryptedId.hashCode()
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<SummonerInGame>(){
            override fun areItemsTheSame(oldItem: SummonerInGame, newItem: SummonerInGame): Boolean {
                return oldItem.summoner.encryptedId == newItem.summoner.encryptedId
            }

            override fun areContentsTheSame(oldItem: SummonerInGame, newItem: SummonerInGame): Boolean {
                val isSame =  oldItem.isLoading == newItem.isLoading &&
                        oldItem.game == newItem.game &&
                        oldItem.summoner == newItem.summoner
                return isSame
            }

        }
    }

}
