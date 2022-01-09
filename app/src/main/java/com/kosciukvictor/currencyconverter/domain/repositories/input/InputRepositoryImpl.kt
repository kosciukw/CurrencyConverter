package com.kosciukvictor.currencyconverter.domain.repositories.input

import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.lang.Exception

@ExperimentalCoroutinesApi
class InputRepositoryImpl: InputRepository {

    override fun clear() = clearEquation()

    override fun removeLastInput(currentEquation: String?) = try {
        val tmpEquation = currentEquation ?: clearEquation()
        when (tmpEquation.length) {
            0, 1 -> clear()
            in 2 until INPUT_LIMIT -> tmpEquation.dropLast(1)
            else -> clear()
        }
    } catch (e: Exception) {
        throw e
    }

    override fun setNumberInput(inputNumber: Int, currentEquation: String?): String = try {
        val tmpEquation = currentEquation ?: clearEquation()

        if (tmpEquation.length > INPUT_LIMIT)
            throw java.lang.IllegalStateException("Equation too long")

        tmpEquation.plus(inputNumber)
    } catch (e: Exception) {
        throw e
    }

    override fun setInputComma(currentEquation: String?) = try {
        if (currentEquation.isNullOrEmpty()) {
            "0."
        } else if (currentEquation.length > INPUT_LIMIT) {
            throw java.lang.IllegalStateException("Equation too long")
        } else if (!currentEquation.contains(".")) {
            currentEquation.plus(".")
        } else
            currentEquation
    } catch (e: Exception) {
        throw e
    }

    private fun clearEquation() = ""

    companion object {
        const val INPUT_LIMIT = 14
    }
}
