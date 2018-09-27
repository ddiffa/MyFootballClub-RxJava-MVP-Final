package com.example.ddiff.myfootballclubapi.network

import com.example.ddiff.myfootballclubapi.mvp.model.MatchResponse
import com.example.ddiff.myfootballclubapi.mvp.model.PlayerResponse
import com.example.ddiff.myfootballclubapi.mvp.model.TeamResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Diffa Dwi Desyawan on 15/9/18.
 * email : diffadwi1@gmail.com.
 */
interface ApiServiceInterface {

    @GET("eventsnextleague.php")
    fun getNextMatch(@Query("id") id :String): Observable<MatchResponse>

    @GET("eventspastleague.php")
    fun getLastMatch(@Query("id") id :String): Observable<MatchResponse>

    @GET("lookupteam.php")
    fun getTeamInfo(@Query("id") id: String?): Observable<TeamResponse>

    @GET("searchteams.php")
    fun getSearchTeam(@Query("t") query: String?): Observable<TeamResponse>

    @GET("searchevents.php")
    fun getSearchMatch(@Query("e") query: String?): Observable<MatchResponse>

    @GET("lookup_all_teams.php")
    fun getAllTeam(@Query("id") id: String): Observable<TeamResponse>

    @GET("lookup_all_players.php")
    fun getAllPlayers(@Query("id") id: String?): Observable<PlayerResponse>


    @GET("lookupevent.php")
    fun getEventById(@Query("id") id:String) : Observable<MatchResponse>


}