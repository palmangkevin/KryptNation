package com.example.kevinkombate.kryptnation.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.kevinkombate.kryptnation.model.Wallet

@Database(entities = [Wallet::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun walletDao(): WalletDao
}