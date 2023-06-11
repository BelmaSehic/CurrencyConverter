package com.dev.currencyconverter.data_interfaqces

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dev.currencyconverter.tables.Rates

@Dao
interface RatesDao {

    @Insert
    suspend fun addRate(rate: Rates)

    @Query("SELECT * FROM rates")
    fun getAllCurrencies(): LiveData<List<Rates>>

    @Delete
    suspend fun deleteCurrency(rate: Rates)

}