package com.example.naufal.football.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DatabaseHelper(ctx: Context) : ManagedSQLiteOpenHelper(
    ctx,
    "MyFavorite.db",
    null, 1
) {

    companion object {
        private var instance: DatabaseHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DatabaseHelper {
            if (instance == null) {
                instance = DatabaseHelper(ctx.applicationContext)
            }
            return instance as DatabaseHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
            FavoriteMatch.TABLE_MATCH_FAVORITE, true,
            FavoriteMatch.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteMatch.ID_EVENT to TEXT + UNIQUE,
            FavoriteMatch.STR_HOME_TEAM to TEXT,
            FavoriteMatch.STR_AWAY_TEAM to TEXT,
            FavoriteMatch.INT_HOME_SCORE to TEXT,
            FavoriteMatch.INT_AWAY_SCORE to TEXT,
            FavoriteMatch.DATE_EVENT to TEXT,
            FavoriteMatch.STR_HOME_LINEUP_GOALKEEPER to TEXT,
            FavoriteMatch.STR_AWAY_LINEUP_GOALKEEPER to TEXT,
            FavoriteMatch.STR_HOME_GOAL_DETAILS to TEXT,
            FavoriteMatch.STR_AWAY_GOAL_DETAILS to TEXT,
            FavoriteMatch.INT_HOME_SHOTS to TEXT,
            FavoriteMatch.INT_AWAY_SHOTS to TEXT,
            FavoriteMatch.STR_HOME_LINEUP_DEFENSE to TEXT,
            FavoriteMatch.AWAY_DEFENSE to TEXT,
            FavoriteMatch.STR_AWAY_LINEUP_DEFENSE to TEXT,
            FavoriteMatch.STR_AWAY_LINEUP_MIDFIELD to TEXT,
            FavoriteMatch.STR_HOME_LINEUP_FORWARD to TEXT,
            FavoriteMatch.STR_AWAY_LINEUP_FORWARD to TEXT,
            FavoriteMatch.STR_HOME_LINEUP_SUBSTITUTES to TEXT,
            FavoriteMatch.STR_AWAY_LINEUP_SUBSTITUTES to TEXT,
            FavoriteMatch.STR_HOME_FORMATION to TEXT,
            FavoriteMatch.STR_AWAY_FORMATION to TEXT,
            FavoriteMatch.STR_TEAM_BADGE to TEXT,
            FavoriteMatch.ID_HOME_TEAM to TEXT,
            FavoriteMatch.ID_AWAY_TEAM to TEXT
        )

        db?.createTable(FavoriteTeam.TABLE_FAVORITE_TEAM, true,
            FavoriteTeam.ID_FAVORITE_TEAM to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteTeam.TEAM_ID to TEXT + UNIQUE,
            FavoriteTeam.TEAM_NAME to TEXT,
            FavoriteTeam.TEAM_BADGE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(FavoriteMatch.TABLE_MATCH_FAVORITE, true)
        db?.dropTable(FavoriteTeam.TABLE_FAVORITE_TEAM, true)
    }

}

val Context.database: DatabaseHelper
    get() = DatabaseHelper.getInstance(applicationContext)