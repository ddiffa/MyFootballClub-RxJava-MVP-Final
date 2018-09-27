package com.example.ddiff.myfootballclubapi.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ddiff.myfootballclubapi.R
import com.example.ddiff.myfootballclubapi.R.layout.item_player
import com.example.ddiff.myfootballclubapi.mvp.model.PlayerModel
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_player.*
import kotlinx.android.synthetic.main.item_player.view.*
/**
 * Created by Diffa Dwi Desyawan on 23/9/18.
 * email : diffadwi1@gmail.com.
 */
class PlayersAdapter(val list: List<PlayerModel>,
                     val context: Context?, fragment: Fragment)
    : RecyclerView.Adapter<PlayersAdapter.ViewHolder>() {

    private val listener: PlayersAdapter.OnItemClickListener

    init {
        this.listener = fragment as PlayersAdapter.OnItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(item_player, parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            listener.itemDetail(list[position])
        }
    }

    inner class ViewHolder(override val containerView: View)
        : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(team: PlayerModel) {
            itemView.tv_player.text = team.strPlayer
            Picasso.get()
                    .load(team.strCutout)
                    .placeholder(R.drawable.ic_loop_white_24dp)
                    .into(img_player)
        }

    }

    interface OnItemClickListener {
        fun itemDetail(model: PlayerModel)
    }
}