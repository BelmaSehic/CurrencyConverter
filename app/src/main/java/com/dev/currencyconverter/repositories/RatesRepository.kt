package com.dev.currencyconverter.repositories

import androidx.lifecycle.LiveData
import com.dev.currencyconverter.data_interfaqces.RatesDao
import com.dev.currencyconverter.tables.Rates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RatesRepository @Inject constructor(val ratesDao: RatesDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun addRates(rate: Rates) {
        coroutineScope.launch {
            ratesDao.addRate(rate)
        }
    }

    fun getRates(): LiveData<List<Rates>> {
        return ratesDao.getAllCurrencies()
    }

}