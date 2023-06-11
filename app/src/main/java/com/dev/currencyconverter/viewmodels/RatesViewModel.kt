package com.dev.currencyconverter.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dev.currencyconverter.repositories.RatesRepository
import com.dev.currencyconverter.tables.Rates
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RatesViewModel @Inject constructor(private val ratesRepository: RatesRepository) : ViewModel() {

    val ratesList: LiveData<List<Rates>>
        get() = ratesRepository.getRates()

    fun addRates(rate: Rates) {
        ratesRepository.addRates(rate)
    }

}