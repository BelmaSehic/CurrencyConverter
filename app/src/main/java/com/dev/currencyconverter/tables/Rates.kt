package com.dev.currencyconverter.tables

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rates")
data class Rates(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "currency1")
    val currency1: String,

    @ColumnInfo(name = "currency2")
    val currency2: String,

    @ColumnInfo(name = "conversionRate")
    val totalConversion: Float
)