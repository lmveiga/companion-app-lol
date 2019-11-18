package com.gmail.lucasmveigabr.companionlol.data.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.gmail.lucasmveigabr.companionlol.data.model.schema.MatchSchema
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SummonerInGame(
    val isLoading: Boolean,
    val summoner: Summoner,
    val game: MatchSchema?
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (other !is SummonerInGame) return false
        return summoner.encryptedId == other.summoner.encryptedId
    }

    override fun hashCode(): Int {
        return summoner.encryptedId.hashCode()
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<SummonerInGame>() {
            override fun areItemsTheSame(
                oldItem: SummonerInGame,
                newItem: SummonerInGame
            ): Boolean {
                return oldItem.summoner.encryptedId == newItem.summoner.encryptedId
            }

            override fun areContentsTheSame(
                oldItem: SummonerInGame,
                newItem: SummonerInGame
            ): Boolean {
                return oldItem.isLoading == newItem.isLoading &&
                        oldItem.game == newItem.game &&
                        oldItem.summoner == newItem.summoner
            }

        }
    }

}
