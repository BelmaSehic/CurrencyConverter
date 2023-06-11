package com.dev.currencyconverter.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dev.currencyconverter.repositories.HistoryRepository
import com.dev.currencyconverter.tables.History
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(val historyRepository: HistoryRepository) : ViewModel() {

    val historyList: LiveData<List<History>>
        get() = historyRepository.getHistory()

    fun addHistory(history: History) {
        historyRepository.addHistory(history)
    }

}