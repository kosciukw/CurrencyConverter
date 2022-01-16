package com.kosciukvictor.currencyconverter.domain.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kosciukvictor.currencyconverter.domain.api.models.ApiRates
import com.kosciukvictor.currencyconverter.domain.usecases.*


class MainViewModel(
    private val clearUseCase: ClearUseCase,
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

    private val _rates: MutableLiveData<ApiRates> = MutableLiveData()
    val rates = _rates

    private val _inputEquation: MutableLiveData<String> = MutableLiveData("")
    val inputEquation = _inputEquation

    private val _outputEquation: MutableLiveData<String> = MutableLiveData()
    val outputEquation = _outputEquation

    private val _prefCurr: MutableLiveData<Map<String, Int>> = MutableLiveData()
    val prefCurr = _prefCurr

    private val _setPrefCurr: MutableLiveData<Unit> = MutableLiveData()

    init {
        getApiRates()
    }


    fun getCurrencyPreferences() =
        getCurrencyPreferencesUseCase(Unit, viewModelScope) { result ->
            result.onSuccess {
                _prefCurr.value = it
            }
            result.onFailure {
                _exception.value = it.localizedMessage
            }
        }

    fun saveCurrencyPreferences(key: String, position: Int) =
        saveCurrencyPreferencesUseCase(Pair(key, position), viewModelScope) { result ->
            result.onSuccess {
                _setPrefCurr.value = Unit
            }
            result.onFailure {
                _exception.value = it.localizedMessage
            }
        }

    fun setNumericalInput(input: Int, equation: String) =
        setNumberUseCase(Pair(input, equation), viewModelScope) { result ->
            result.onSuccess {
                _inputEquation.value = it
                convertValues(prefCurr.value, rates.value?.rates, it)
            }
            result.onFailure {
                _exception.value = it.message
            }
        }

    fun setInputComma(equation: String) =
        setCommaUseCase(equation, viewModelScope) { result ->
            result.onSuccess {
                _inputEquation.value = it
                convertValues(prefCurr.value, rates.value?.rates, it)
            }
            result.onFailure {
                _exception.value = it.message
            }
        }

    fun removeLastInput(equation: String) =
        removeLastInputUseCase(equation, viewModelScope) { result ->
            result.onSuccess {
                _inputEquation.value = it
                convertValues(prefCurr.value, rates.value?.rates, it)
            }
            result.onFailure {
                _exception.value = it.message
            }
        }

    fun clear() =
        clearUseCase(Unit, viewModelScope) { result ->
            result.onSuccess {
                _inputEquation.value = it
                _outputEquation.value = it
            }
            result.onFailure {
                _exception.value = it.message
            }
        }

    fun convertValues(
        map: Map<String, Int>?,
        rates: Map<String, Double>?,
        equation: String
    ) =
        convertUseCase(Triple(map, rates, equation), viewModelScope) { result ->
            result.onSuccess {
                _outputEquation.value = it
            }

            result.onFailure {
                _exception.value = it.message
            }
        }

    fun getApiRates() =
        getRatesUseCase(Unit, viewModelScope) { result ->
            result.onSuccess {
                _rates.value = it
                getCurrencyPreferences()
            }
            result.onFailure {
                _exception.value = it.localizedMessage
            }
        }
}

