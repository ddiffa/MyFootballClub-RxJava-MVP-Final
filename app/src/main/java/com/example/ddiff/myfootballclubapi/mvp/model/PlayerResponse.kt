package com.example.ddiff.myfootballclubapi.mvp.model

import com.google.gson.annotations.SerializedName
/**
 * Created by Diffa Dwi Desyawan on 23/9/18.
 * email : diffadwi1@gmail.com.
 */

data class PlayerResponse(
        @SerializedName("players")
        var players: List<PlayerModel>,
        @SerializedName("player")
        var player: List<PlayerModel>)
