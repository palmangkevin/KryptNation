package com.example.kevinkombate.kryptnation.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity
data class Wallet( @PrimaryKey @NonNull @ColumnInfo(name = "address") val address: String,
                   @ColumnInfo(name = "name") val name: String,
                   @NonNull @ColumnInfo(name = "bookmarked") val bookmarked : Boolean,
                   @ColumnInfo(name = "date") val date : Long)