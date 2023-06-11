package com.dev.currencyconverter.data_interfaqces

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dev.currencyconverter.tables.History
import com.dev.currencyconverter.tables.Rates

@Dao
interface HistoryDao {
    @Insert
    suspend fun addHistory(history: History)

    @Query("SELECT * FROM history")
    fun getAllHistory(): LiveData<List<History>>

    @Delete
    suspend fun deleteHistory(history: History)
}