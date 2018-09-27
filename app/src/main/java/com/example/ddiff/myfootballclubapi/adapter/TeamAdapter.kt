package com.example.ddiff.myfootballclubapi.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ddiff.myfootballclubapi.R
import com.example.ddiff.myfootballclubapi.mvp.model.TeamModel
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_teams.*
import kotlinx.android.synthetic.main.item_teams.view.*
/**
 * Created by Diffa Dwi Desyawan on 23/9/18.
 * email : diffadwi1@gmail.com.
 */
class TeamAdapter(val teamList: List<TeamModel>,
                  val context: Context?, fragment: Fragment)
    : RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {
    private val listener: TeamAdapter.OnItemClickListener

    init {
        this.listener = fragment as TeamAdapter.OnItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(LayoutInflater.from(context).inflate(R.layout.item_teams, parent, false))
    }

    override fun getItemCount() = teamList.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(teamList[position])
        holder.itemView.setOnClickListener {
            listener.itemDetail(teamList[position])
        }
    }

    inner class TeamViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(team: TeamModel) {
            itemView.tvTeam.text = team.strTeam
            Picasso.get()
                    .load(team.strTeamBadge)
                    .placeholder(R.drawable.ic_loop_white_24dp)
                    .into(imgTeam)
        }

    }

    interface OnItemClickListener {
        fun itemDetail(model: TeamModel)
    }
}