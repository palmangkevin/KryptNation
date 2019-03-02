package com.example.kevinkombate.kryptnation

import android.app.Application
import android.arch.persistence.room.Room
import com.example.kevinkombate.kryptnation.database.AppDatabase


class App: Application() {

    companion object {
        lateinit var appDatabase: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        appDatabase = Room.databaseBuilder(applicationContext,
                AppDatabase::class.java, "kryptnation_db").build()
    }

}