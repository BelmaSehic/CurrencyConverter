package com.dev.currencyconverter.repositories

import androidx.lifecycle.LiveData
import com.dev.currencyconverter.data_interfaqces.HistoryDao
import com.dev.currencyconverter.tables.History
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HistoryRepository @Inject constructor(val historyDao: HistoryDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun addHistory(history: History) {
        coroutineScope.launch {
            historyDao.addHistory(history)
        }
    }

    fun getHistory(): LiveData<List<History>> {
        return historyDao.getAllHistory()
    }

}