package com.example.patientrecord.config

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val DATABASE_NAME = "patients"
const val DATABASE_VERSION = 1

const val TABLE_PATIENT = "patient"

class AppDataBase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {

        val query = "CREATE TABLE $TABLE_PATIENT(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "name TEXT NOT NULL, " +
                "gender TEXT NOT NULL, " +
                "mobile TEXT NOT NULL, " +
                "email TEXT NOT NULL, " +
                "address TEXT NOT NULL, " +
                "age INTEGER DEFAULT 0, " +
                "result_value TEXT NULL, " +
                "uuid TEXT NULL, " +
                "imei TEXT NULL, " +
                "version TEXT NULL, " +
                "result_comment TEXT NULL, " +
                "app_name TEXT NULL, " +
                "photo TEXT NOT NULL, " +
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "modified_at DATETIME DEFAULT CURRENT_TIMESTAMP);"

        try {
            db?.execSQL(query)
        }catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}