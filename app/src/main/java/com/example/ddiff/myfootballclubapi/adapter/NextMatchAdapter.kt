package com.example.ddiff.myfootballclubapi.adapter

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ddiff.myfootballclubapi.R
import com.example.ddiff.myfootballclubapi.mvp.model.MatchModel
import com.example.ddiff.myfootballclubapi.mvp.ui.activity.detail.DetailActivity
import com.example.ddiff.myfootballclubapi.utils.getKeyIntent
import com.example.ddiff.myfootballclubapi.utils.getSimpleDate
import com.example.ddiff.myfootballclubapi.utils.getSimpleTime
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_next_match.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
/**
 * Created by Diffa Dwi Desyawan on 25/9/18.
 * email : diffadwi1@gmail.com.
 */
class NextMatchAdapter(private val list: List<MatchModel>, fragment: Fragment)
    : RecyclerView.Adapter<NextMatchAdapter.Holder>() {

    private val listener: NextMatchAdapter.OnItemClickListener

    init {
        this.listener = fragment as NextMatchAdapter.OnItemClickListener
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_next_match, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindItem(list[position])
        holder.itemView.setOnClickListener {
            listener.itemDetail(list.get(position))
        }
        holder.itemView.img_reminder.setOnClickListener {
            listener.reminder(list.get(position))
        }
    }


    class Holder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(items: MatchModel) {
            itemView.tv_home_club_next.text = items.strHomeTeam
            itemView.tv_away_club_next.text = items.strAwayTeam
            itemView.tv_score_home_club_next.text = items.intHomeScore
            itemView.tv_score_away_club_next.text = items.intAwayScore
            itemView.tv_date_next.text = getSimpleDate(items.strDate)
            itemView.tv_time_next.text = getSimpleTime(items.strTime)
            itemView.setOnClickListener {
                itemView.context.startActivity<DetailActivity>(getKeyIntent() to items)
            }
            itemView.img_reminder.setOnClickListener {
                itemView.context.toast("Can't add reminder in this class")
            }
        }

    }

    interface OnItemClickListener {
        fun itemDetail(model: MatchModel)
        fun reminder(model: MatchModel)
    }

}