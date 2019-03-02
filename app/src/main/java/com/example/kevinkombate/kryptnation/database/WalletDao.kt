package com.example.kevinkombate.kryptnation.database

import android.arch.persistence.room.*

import com.example.kevinkombate.kryptnation.model.Wallet


@Dao
interface WalletDao {

    @Query("SELECT * FROM wallet ORDER BY date DESC")
    fun getAll(): List<Wallet>

    @Query("SELECT * FROM wallet WHERE bookmarked = :isBookmarked")
    fun getBookmarked(isBookmarked: Boolean = true): List<Wallet>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: Wallet)

    @Delete
    fun delete(wallet: Wallet)


}