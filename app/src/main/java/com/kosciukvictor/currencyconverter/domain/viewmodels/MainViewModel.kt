package com.kosciukvictor.currencyconverter.domain.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kosciukvictor.currencyconverter.domain.api.models.ApiRates
import com.kosciukvictor.currencyconverter.domain.converter.models.ConvertData
import com.kosciukvictor.currencyconverter.domain.usecases.*


class MainViewModel(
    private val clearEquationUseCase: ClearEquationUseCase,
    private val setCommaUseCase: SetCommaUseCase,
    private val setNumberUseCase: SetNumberUseCase,
    private val removeLastInputUseCase: RemoveLastInputUseCase,
    private val convertUseCase: ConvertUseCase,
    private val getRatesUseCase: GetRatesUseCase,
    private val getCurrencyPreferencesUseCase: GetCurrencyPreferencesUseCase,
    private val saveCurrencyPreferencesUseCase: SaveCurrencyPreferencesUseCase
) : ViewModel() {

    private val _exception: MutableLiveData<String> = MutableLiveData()
    val exception = _exception

    private val _currencyRates: MutableLiveData<ApiRates> = MutableLiveData()
    val currencyRates = _currencyRates

    private val _inputEquation: MutableLiveData<String> = MutableLiveData("")
    val inputEquation = _inputEquation

    private val _outputEquation: MutableLiveData<String> = MutableLiveData()
    val outputEquation = _outputEquation

    private val _preferredCurrencies: MutableLiveData<Map<String, Int>> = MutableLiveData()
    val preferredCurrencies = _preferredCurrencies

    private val _setPrefCurr: MutableLiveData<Unit> = MutableLiveData()

    init {
        getApiRates()
    }

    fun onGetCurrencyPreferences() {
        getCurrencyPreferences()
    }

    fun onSetInputComma(equation: String) {
        setInputComma(equation)
    }

    fun onGetApiRates() {
        getApiRates()
    }
    fun onClearEquation() {
        clearEquation()
    }

    fun onRemoveLastInput(equation: String) {
        removeLastInput(equation)
    }

    fun onSaveCurrencyPreferences(key: String, position: Int) {
        saveCurrencyPreferences(key, position)
    }

    fun onSetNumericalInput(input: Int, equation: String) {
        setNumericalInput(input, equation)
    }

    private fun getCurrencyPreferences() {
        getCurrencyPreferencesUseCase(Unit, viewModelScope) { result ->

            result.onSuccess {
                _preferredCurrencies.value = it
            }

            result.onFailure {
                _exception.value = it.localizedMessage
            }
        }
    }

    private fun saveCurrencyPreferences(key: String, position: Int) {
        saveCurrencyPreferencesUseCase(Pair(key, position), viewModelScope) { result ->

            result.onSuccess {
                _setPrefCurr.value = Unit
            }

            result.onFailure {
                _exception.value = it.localizedMessage
            }
        }
    }

    private fun setNumericalInput(input: Int, equation: String) {
        setNumberUseCase(Pair(input, equation), viewModelScope) { result ->
            result.onSuccess {
                _inputEquation.value = it
                convertValues(preferredCurrencies.value, currencyRates.value?.rates, it)
            }
            result.onFailure {
                _exception.value = it.message
            }
        }
    }

    private fun setInputComma(equation: String) {
        setCommaUseCase(equation, viewModelScope) { result ->
            result.onSuccess {
                _inputEquation.value = it
                convertValues(preferredCurrencies.value, currencyRates.value?.rates, it)
            }
            result.onFailure {
                _exception.value = it.message
            }
        }
    }

    private fun removeLastInput(equation: String) {
        removeLastInputUseCase(equation, viewModelScope) { result ->

            result.onSuccess {
                _inputEquation.value = it
                convertValues(preferredCurrencies.value, currencyRates.value?.rates, it)
            }

            result.onFailure {
                _exception.value = it.message
            }
        }
    }

    private fun clearEquation() {
        clearEquationUseCase(Unit, viewModelScope) { result ->

            result.onSuccess {
                _inputEquation.value = it
                _outputEquation.value = it
            }

            result.onFailure {
                _exception.value = it.message
            }
        }
    }


   private fun convertValues(
        map: Map<String, Int>?,
        rates: Map<String, Double>?,
        equation: String
    ) {
        convertUseCase(
            ConvertData(
                ratesIndexMap = map,
                ratesValuesMap = rates,
                currentEquation = equation
            ), viewModelScope
        ) { result ->

            result.onSuccess {
                _outputEquation.value = it
            }

            result.onFailure {
                _exception.value = it.message
            }
        }
    }

    private fun getApiRates() {
        getRatesUseCase(Unit, viewModelScope) { result ->

            result.onSuccess {
                _currencyRates.value = it
                getCurrencyPreferences()
            }

            result.onFailure {
                _exception.value = it.localizedMessage
            }
        }
    }
}

