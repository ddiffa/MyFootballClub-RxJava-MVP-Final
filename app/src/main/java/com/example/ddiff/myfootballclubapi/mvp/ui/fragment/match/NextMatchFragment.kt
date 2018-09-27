package com.example.ddiff.myfootballclubapi.mvp.ui.fragment.match


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Toast
import com.example.ddiff.myfootballclubapi.R
import com.example.ddiff.myfootballclubapi.adapter.NextMatchAdapter
import com.example.ddiff.myfootballclubapi.helper.CalendarHelper
import com.example.ddiff.myfootballclubapi.mvp.model.MatchModel
import com.example.ddiff.myfootballclubapi.mvp.presenter.nextmatch.NextMatchContract
import com.example.ddiff.myfootballclubapi.mvp.presenter.nextmatch.NextMatchPresenter
import com.example.ddiff.myfootballclubapi.mvp.ui.activity.detail.DetailActivity
import com.example.ddiff.myfootballclubapi.network.ApiService
import com.example.ddiff.myfootballclubapi.utils.*
import kotlinx.android.synthetic.main.fragment_next_match.*
import org.jetbrains.anko.support.v4.startActivity
import java.text.SimpleDateFormat


/**
 * Created by Diffa Dwi Desyawan on 15/9/18.
 * email : diffadwi1@gmail.com.
 */
class NextMatchFragment : Fragment(), NextMatchContract.View, NextMatchAdapter.OnItemClickListener {

    private lateinit var presenter: NextMatchContract.Presenter
    private var rvMatch: RecyclerView? = null
    private var leagueName: String? = null
    private lateinit var progressBar: ProgressBar

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val mRootView = inflater.inflate(R.layout.fragment_next_match, container, false)
        rvMatch = mRootView.findViewById(R.id.rv_fragment_next_match)
        progressBar = mRootView.findViewById(R.id.progressBar)

        return mRootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadPresenter()
        val spinnerItem = resources.getStringArray(R.array.leagueArray)
        val spinnerAdapter = ArrayAdapter(activity, android.R.layout.simple_spinner_dropdown_item, spinnerItem)

        spinnerNextMatch.adapter = spinnerAdapter

        spinnerNextMatch.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                leagueName = spinnerNextMatch.selectedItem.toString()
                when (leagueName) {
                    "English Premier League" -> presenter.getNextMatch("4328")
                    "German Bundesliga" -> presenter.getNextMatch("4331")
                    "Italian Serie A" -> presenter.getNextMatch("4332")
                    "French Ligue 1" -> presenter.getNextMatch("4334")
                    "Spanish La Liga" -> presenter.getNextMatch("4335")
                    "Netherlands Eredivisie" -> presenter.getNextMatch("4337")
                    else -> presenter.getNextMatch("4328")
                }
            }

        }
    }

    private fun loadPresenter() {
        val request = ApiService()
        val scheduler = AppScheduler()
        presenter = NextMatchPresenter(this, request, scheduler)
        presenter.getNextMatch("4328")
    }

    override fun onLoadClub(list: List<MatchModel>) {
        val adapter = NextMatchAdapter(list, this)
        rvMatch!!.layoutManager = LinearLayoutManager(activity)
        rvMatch!!.adapter = adapter
    }

    override fun showProgress() {
        progressBar.visible()
    }

    override fun showLoadErrorMessage(throwable: Throwable?) {
        Toast.makeText(context, "Failed to display list. \n ${throwable?.localizedMessage}", Toast.LENGTH_LONG).show()
    }

    override fun hideProgress() {
        progressBar.invisible()
    }

    override fun itemDetail(model: MatchModel) {
        startActivity<DetailActivity>(getKeyIntent() to model, getKeyActivity() to "match")
    }

    override fun reminder(model: MatchModel) {
        if (CalendarHelper.haveCalendarReadWritePermissions(activity)) {
            addEventToGoogleCalendar(model);
        } else {
            CalendarHelper.requestCalendarReadWritePermission(activity);
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == CalendarHelper.REQUEST_CODE) {
            if (CalendarHelper.haveCalendarReadWritePermissions(activity)) {
                Toast.makeText(context, "Permission",
                        Toast.LENGTH_LONG).show()
            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @SuppressLint("MissingPermission", "Recycle")
    private fun getGoogleCalendarId(): Long {

        val event =
                arrayOf(CalendarContract.Calendars._ID,
                        CalendarContract.Calendars.NAME,
                        CalendarContract.Calendars.ACCOUNT_NAME,
                        CalendarContract.Calendars.ACCOUNT_TYPE)

        if (CalendarHelper.haveCalendarReadWritePermissions(activity)) {
            val cursor = context?.contentResolver
                    ?.query(CalendarContract.Calendars.CONTENT_URI, event,
                            CalendarContract.Calendars.VISIBLE + " = 1",
                            null,
                            CalendarContract.Calendars._ID + " ASC")

            if (cursor!!.moveToFirst()) {
                do {
                    val id = cursor.getLong(0)
                    return id

                } while (cursor.moveToNext())
            }
        }
        return -1

    }

    @SuppressLint("SimpleDateFormat")
    private fun addEventToGoogleCalendar(model: MatchModel) {
        val calId = getGoogleCalendarId()
        if (calId == -1L) {
            Toast.makeText(context, "Somethings went wrong, try again!",
                    Toast.LENGTH_SHORT).show()
            return
        }
        val title = model.strEvent
        val times = model.strTime?.split("+")?.get(0)
        val date = model.strDate
        val dateTimePick = "$date $times"
        val simpleDate = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        val newDate = simpleDate.parse(dateTimePick)
        val timeInMillis = newDate.time
        val end = timeInMillis + 5400000
        val intent = Intent(Intent.ACTION_EDIT)
        intent.type = "vnd.android.cursor.item/event"
        intent.putExtra(CalendarContract.Events.TITLE, title)
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, timeInMillis)
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end)
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Television")
        intent.putExtra(CalendarContract.Events.DESCRIPTION, title)
        startActivity(intent)
    }

}
