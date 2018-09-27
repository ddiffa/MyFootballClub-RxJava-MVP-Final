package com.example.ddiff.myfootballclubapi.mvp.model.db

class FavoriteTeamDB(
        val id: Long?,
        val idTeam: String
) {
    companion object {
        const val TABLE_TEAM_NAME: String = "TABLE_TEAM_NAME"
        const val ID: String = "ID_"
        const val TEAM_ID: String = "TEAM_ID"
    }
}