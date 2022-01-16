package com.kosciukvictor.currencyconverter.domain.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kosciukvictor.currencyconverter.domain.usecases.*
import com.kosciukvictor.currencyconverter.domain.utils.todo



class MainViewModel(
    private val clearUseCase: ClearUseCase,
    private val setCommaUseCase: SetCommaUseCase,
    private val setNumberUseCase: SetNumberUseCase,
    private val removeLastInputUseCase: RemoveLastInputUseCase,
    private val convertUseCase: ConvertUseCase
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
        setNumberUseCase(Pair(input, equation), viewModelScope) { result ->
            result.onSuccess {
                _inputEquation.value = it
            }
            result.onFailure {
                _exception.value = it.message
            }
        }

    fun setInputComma(equation: String) =
        setCommaUseCase(equation, viewModelScope) { result ->
            result.onSuccess {
                _inputEquation.value = it
            }
            result.onFailure {
                _exception.value = it.message
            }
        }

    fun removeLastInput(equation: String) =
        removeLastInputUseCase(equation, viewModelScope) { result ->
            result.onSuccess {
                _inputEquation.value = it
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
        todo()
}

