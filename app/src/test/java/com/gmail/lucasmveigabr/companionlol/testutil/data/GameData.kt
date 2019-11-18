package com.gmail.lucasmveigabr.companionlol.testutil.data

import com.gmail.lucasmveigabr.companionlol.data.model.*
import com.gmail.lucasmveigabr.companionlol.data.model.schema.ChampionSchema
import com.gmail.lucasmveigabr.companionlol.data.model.schema.ImagePropertiesSchema
import com.gmail.lucasmveigabr.companionlol.data.model.schema.MatchSchema
import com.gmail.lucasmveigabr.companionlol.data.model.schema.SpellSummSchema

const val MAIN_SUMMONER = "mainSummoner"
const val MAIN_SUMMONER_NAME = "MainSummonerName"
const val ALLIES_NAME = "allie"
const val ENEMY_NAME = "enemy"

object GameData {


    fun getListOfSummoners() = listOf(
        Summoner("id1", "name1", Region.BR),
        Summoner("id2", "name2", Region.BR),
        Summoner("id3", "name3", Region.BR)
    )


    fun getSpellSchema() = SpellSummSchema(
        arrayListOf(100.0),
        "id",
        ImagePropertiesSchema(
            "",
            1,
            1,
            1,
            1
        ), "1", "name"
    )

    fun getChampionSchema() = ChampionSchema(
        "version",
        "id",
        1,
        "champion",
        "title"
    )

    fun getGameData(): SummonerInGame {
        return SummonerInGame(
            false, getSummoner(), getSummonerMatchStatus(getParticipants())
        )
    }

    fun getGameDataWithCdrPerk(): SummonerInGame {
        return SummonerInGame(
            false, getSummoner(), getSummonerMatchStatus(
                getParticipantsWithCdrPerk()
            )
        )
    }

    fun getSummonerMatchStatus(participants: List<Participant>) =
        MatchSchema(participants)

    fun getParticipants(): List<Participant> {
        return listOf(
            getParticipant(getSummoner().encryptedId, 1, getSummoner().summonnerName),
            getParticipant("id2", 1, ALLIES_NAME), getParticipant("id3", 1, ALLIES_NAME),
            getParticipant("id4", 1, ALLIES_NAME), getParticipant("id5", 1, ALLIES_NAME),
            getParticipant("id1", 2, ENEMY_NAME), getParticipant("id2", 2, ENEMY_NAME),
            getParticipant("id3", 2, ENEMY_NAME), getParticipant("id4", 2, ENEMY_NAME),
            getParticipant("id5", 2, ENEMY_NAME)
        )
    }

    private fun getParticipantsWithCdrPerk(): List<Participant> {
        return listOf(
            getParticipantWithCdrPerk(getSummoner().encryptedId, 1, getSummoner().summonnerName),
            getParticipantWithCdrPerk("id2", 1, ALLIES_NAME),
            getParticipantWithCdrPerk("id3", 1, ALLIES_NAME),
            getParticipantWithCdrPerk("id4", 1, ALLIES_NAME),
            getParticipantWithCdrPerk("id5", 1, ALLIES_NAME),
            getParticipantWithCdrPerk("id1", 2, ENEMY_NAME),
            getParticipantWithCdrPerk("id2", 2, ENEMY_NAME),
            getParticipantWithCdrPerk("id3", 2, ENEMY_NAME),
            getParticipantWithCdrPerk("id4", 2, ENEMY_NAME),
            getParticipantWithCdrPerk("id5", 2, ENEMY_NAME)
        )
    }

    private fun getParticipantWithCdrPerk(id: String, teamID: Int, name: String): Participant {
        return Participant(1, Perks(
                listOf(8347), 0,
                0
            ), 0, 1, 1, id, name, teamID
        )
    }

    private fun getParticipant(id: String, teamID: Int, name: String): Participant {
        return Participant(1, Perks(
                ArrayList(), 0,
                0
            ), 0, 1, 1, id, name, teamID
        )
    }

    private fun getSummoner() = Summoner(MAIN_SUMMONER, MAIN_SUMMONER_NAME, Region.BR)

}