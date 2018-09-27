package com.example.ddiff.myfootballclubapi.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.ddiff.myfootballclubapi.mvp.model.db.FavoriteMatchDB
import com.example.ddiff.myfootballclubapi.mvp.model.db.FavoriteTeamDB
import org.jetbrains.anko.db.*

/**
 * Created by Diffa Dwi Desyawan on 21/9/18.
 * email : diffadwi1@gmail.com.
 */
class MyDBOpenHelper(context: Context) : ManagedSQLiteOpenHelper(context,
        "myFootball.db",
        null,
        1) {

    companion object {
        private var instance: MyDBOpenHelper? = null

        fun getInstance(context: Context): MyDBOpenHelper {
            if (instance == null) {
                instance = MyDBOpenHelper(context.applicationContext)
            }
            return instance as MyDBOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(FavoriteMatchDB.TABLE_FAVORITE, true,
                FavoriteMatchDB.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteMatchDB.EVENT_ID to TEXT + UNIQUE,
                FavoriteMatchDB.HOME_TEAM_ID to TEXT,
                FavoriteMatchDB.AWAY_TEAM_ID to TEXT)
        db?.createTable(FavoriteTeamDB.TABLE_TEAM_NAME, true,
                FavoriteTeamDB.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteTeamDB.TEAM_ID to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.dropTable(FavoriteMatchDB.TABLE_FAVORITE, true)
        db?.dropTable(FavoriteTeamDB.TABLE_TEAM_NAME, true)
    }
}


val Context.database: MyDBOpenHelper
    get() = MyDBOpenHelper.getInstance(applicationContext)