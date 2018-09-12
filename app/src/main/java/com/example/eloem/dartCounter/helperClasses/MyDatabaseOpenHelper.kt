package com.example.eloem.dartCounter.helperClasses

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.eloem.dartCounter.util.*
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(context: Context): ManagedSQLiteOpenHelper(context, "MyDatabase", null, 2) {
    override fun onCreate(db: SQLiteDatabase?) {
    
        db?.createTable(Games.tableName, true,
                Games.columnID to INTEGER + PRIMARY_KEY + UNIQUE,
                Games.columnMode to TEXT,
                Games.columnCurrentPlayerPos to INTEGER,
                Games.columnDate to INTEGER)
    
        db?.createTable(Players.tableName, true,
                Players.columnID to INTEGER + PRIMARY_KEY + UNIQUE,
                Players.columnGameID to INTEGER,
                Players.columnName to TEXT,
                Players.columnPoints to INTEGER,
                Players.columnPosition to INTEGER)
    
        db?.createTable(Turns.tableName, true,
                Turns.columnThrowOne to INTEGER,
                Turns.columnThrowTwo to INTEGER,
                Turns.columnThrowThree to INTEGER,
                Turns.columnPlayerID to INTEGER,
                Turns.columnPosition to INTEGER,
                Turns.columnPointsLeft to INTEGER,
                Turns.columnPointsBefore to INTEGER)
    }
    
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    
        db?.dropTable(Games.tableName)
        db?.dropTable(Players.tableName)
        db?.dropTable(Turns.tableName)
        
        db?.createTable(Games.tableName, true,
                Games.columnID to INTEGER + PRIMARY_KEY + UNIQUE,
                Games.columnMode to TEXT,
                Games.columnCurrentPlayerPos to INTEGER,
                Games.columnDate to INTEGER)
    
        db?.createTable(Players.tableName, true,
                Players.columnID to INTEGER + PRIMARY_KEY + UNIQUE,
                Players.columnGameID to INTEGER,
                Players.columnName to TEXT,
                Players.columnPoints to INTEGER,
                Players.columnPosition to INTEGER)
    
        db?.createTable(Turns.tableName, true,
                Turns.columnThrowOne to INTEGER,
                Turns.columnThrowTwo to INTEGER,
                Turns.columnThrowThree to INTEGER,
                Turns.columnPlayerID to INTEGER,
                Turns.columnPosition to INTEGER,
                Turns.columnPointsLeft to INTEGER,
                Turns.columnPointsBefore to INTEGER)
    }
    
    companion object {
        private var instance: MyDatabaseOpenHelper? = null
    
        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }
}

val Context.database get() = MyDatabaseOpenHelper.getInstance(applicationContext)