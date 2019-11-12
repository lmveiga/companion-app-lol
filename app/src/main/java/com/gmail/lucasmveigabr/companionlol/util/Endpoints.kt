package com.gmail.lucasmveigabr.companionlol.util

import com.gmail.lucasmveigabr.companionlol.model.Region

object Endpoints {
    private const val DD_VERSION = "9.22.1"

    private fun getPrefixForRegion(region: Region) =
        when (region) {
            Region.BR -> "br1"
            Region.NA -> "na1"
            Region.EUW -> "euw1"
            Region.EUNE -> "eun1"
            Region.LAN -> "la1"
            Region.LAS -> "la2"
            Region.OCE -> "oc1"
            Region.RU -> "ru"
            Region.TR -> "tr1"
            Region.JP -> "jp1"
            Region.KR -> "kr"
        }

    private fun getBaseUrl(region: Region): String {
        return "https://${getPrefixForRegion(region)}.api.riotgames.com"
    }

    fun summonerInfo(region: Region, name: String): String {
        return "${getBaseUrl(region)}/lol/summoner/v4/summoners/by-name/${name}"
    }

    fun gameInfo(region: Region, encryptedID: String): String {
        return "${getBaseUrl(region)}/lol/spectator/v4/active-games/by-summoner/${encryptedID}"
    }

    fun champions(): String {
        return "http://ddragon.leagueoflegends.com/cdn/${DD_VERSION}/data/en_US/champion.json"
    }

    fun championCard(championID: String): String {
        return "http://ddragon.leagueoflegends.com/cdn/img/champion/loading/${championID}_0.jpg"
    }

    fun championIcon(championID: String): String {
        return "http://ddragon.leagueoflegends.com/cdn/${DD_VERSION}/img/champion/${championID}.png"
    }

    fun summonersSpells(): String {
        return "http://ddragon.leagueoflegends.com/cdn/${DD_VERSION}/data/en_US/summoner.json"
    }

    fun spellIcon(filename: String): String {
        return "http://ddragon.leagueoflegends.com/cdn/${DD_VERSION}/img/spell/${filename}"
    }

    fun version(): String {
        return "https://ddragon.leagueoflegends.com/api/versions.json"
    }
    //TODO("Look for version at app start so that Ddragon version isn't hardcoded")

}