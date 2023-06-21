package com.kosciukvictor.currencyconverter.domain.repositories.input

interface InputRepository {

    fun removeLastInput(currentEquation: String?): String

    fun setNumberInput(inputNumber: Int, currentEquation: String?): String

    fun setInputComma(currentEquation: String?): String

    fun clearEquation(): String
}