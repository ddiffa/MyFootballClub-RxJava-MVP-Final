package com.example.ddiff.myfootballclubapi.mvp.ui.fragment.player


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.ddiff.myfootballclubapi.R
import com.example.ddiff.myfootballclubapi.adapter.PlayersAdapter
import com.example.ddiff.myfootballclubapi.mvp.model.PlayerModel
import com.example.ddiff.myfootballclubapi.mvp.model.TeamModel
import com.example.ddiff.myfootballclubapi.mvp.presenter.player.PlayersContract
import com.example.ddiff.myfootballclubapi.mvp.presenter.player.PlayersPresenter
import com.example.ddiff.myfootballclubapi.mvp.ui.activity.playerDetail.PlayerDetailActivity
import com.example.ddiff.myfootballclubapi.network.ApiService
import com.example.ddiff.myfootballclubapi.utils.AppScheduler
import com.example.ddiff.myfootballclubapi.utils.getKeyActivity
import org.jetbrains.anko.support.v4.startActivity

/**
 * Created by Diffa Dwi Desyawan on 24/9/18.
 * email : diffadwi1@gmail.com.
 */

class PlayerFragment : Fragment(), PlayersContract.View, PlayersAdapter.OnItemClickListener {


    private lateinit var playersPresenter: PlayersContract.Presenter
    private var listPlayer: MutableList<PlayerModel> = mutableListOf()
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val mRootView = inflater.inflate(R.layout.fragment_player, container, false)
        recyclerView = mRootView.findViewById(R.id.rv_player)
        return mRootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val team: TeamModel? = arguments?.getParcelable("teams")
        val request = ApiService()
        val scheduler = AppScheduler()
        playersPresenter = PlayersPresenter(this, request, scheduler)
        playersPresenter.getPlayers(team?.idTeam)
    }

    override fun onLoadPlayers(list: List<PlayerModel>) {
        listPlayer.clear()
        listPlayer.addAll(list)
        val layoutManager = GridLayoutManager(context, 4)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = PlayersAdapter(listPlayer, context, this)
    }


    override fun showLoadErrorMessage(throwable: Throwable?) {
        Toast.makeText(context, "Failed to display list. \n ${throwable?.localizedMessage}", Toast.LENGTH_LONG).show()
    }


    override fun itemDetail(model: PlayerModel) {
        startActivity<PlayerDetailActivity>(getKeyActivity() to model)
    }
}
