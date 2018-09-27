package com.example.ddiff.myfootballclubapi.mvp.ui.activity.playerDetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.ddiff.myfootballclubapi.R
import com.example.ddiff.myfootballclubapi.mvp.model.PlayerModel
import com.example.ddiff.myfootballclubapi.utils.getKeyActivity
import com.example.ddiff.myfootballclubapi.utils.getSimpleDate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_player_detail.*
import kotlinx.android.synthetic.main.layout_player_detail.*

/**
 * Created by Diffa Dwi Desyawan on 23/9/18.
 * email : diffadwi1@gmail.com.
 */
class PlayerDetailActivity : AppCompatActivity() {

    private lateinit var playerModel: PlayerModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)
        setSupportActionBar(toolbar_player_detail)
        playerModel = intent.getParcelableExtra(getKeyActivity())
        supportActionBar?.title = playerModel.strPlayer
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initView()

    }

    private fun initView() {
        Picasso.get()
                .load(playerModel.strThumb)
                .placeholder(R.drawable.ic_loop_white_24dp)
                .into(img_banner_player)
        Picasso.get()
                .load(playerModel.strCutout)
                .placeholder(R.drawable.ic_loop_black_24dp)
                .into(img_player_detail)
        tv_name_player.text = playerModel.strPlayer
        tv_position_player.text = playerModel.strPosition
        tv_birthday.text = getSimpleDate(playerModel.dateBorn)
        tv_player_overview.text = playerModel.strDescriptionEN
        tv_height.text = playerModel.strHeight
        tv_weight.text = playerModel.strWeight
    }
}
