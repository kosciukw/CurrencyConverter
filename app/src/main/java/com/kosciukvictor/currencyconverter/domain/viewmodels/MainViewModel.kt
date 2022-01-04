package com.kosciukvictor.currencyconverter.domain.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kosciukvictor.currencyconverter.domain.utils.todo


class MainViewModel(
) : ViewModel() {

    private val _exception: MutableLiveData<String> = MutableLiveData()
    val exception = _exception

    private val _rates: MutableLiveData<Any> = MutableLiveData()
    val rates = _rates // TODO: 04.01.2022

    private val _inputEquation: MutableLiveData<String> = MutableLiveData("")
    val inputEquation = _inputEquation

    private val _outputEquation: MutableLiveData<String> = MutableLiveData()
    val outputEquation = _outputEquation

    private val _prefCurr: MutableLiveData<Map<String, Int>> = MutableLiveData()
    val prefCurr = _prefCurr

    private val _setPrefCurr: MutableLiveData<Unit> = MutableLiveData()

    init {
//        getApiRates()
    }


    fun getCurrencyPreferences() =
        todo()

    fun saveCurrencyPreferences(key: String, position: Int) =
        todo()

    fun setNumericalInput(input: Int, equation: String) =
        todo()

    fun setInputComma(equation: String) =
        todo()

    fun removeLastInput(equation: String) =
        todo()

    fun clear() =
        todo()

    fun convertValues(
        map: Map<String, Int>?,
        rates: Map<String, Double>?,
        equation: String
    ) =
        todo()

    fun getApiRates() =
        todo()
}

