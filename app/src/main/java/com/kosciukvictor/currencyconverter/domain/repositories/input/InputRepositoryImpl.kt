package com.kosciukvictor.currencyconverter.domain.repositories.input

class InputRepositoryImpl : InputRepository {

    override fun clearEquation(): String {
        return clear()
    }

    override fun removeLastInput(currentEquation: String?): String {

        val tmpEquation = currentEquation ?: clear()

        return when (tmpEquation.length) {
            0, 1 -> clear()
            in 2 until INPUT_LIMIT -> tmpEquation.dropLast(1)
            else -> clear()
        }
    }

    override fun setNumberInput(inputNumber: Int, currentEquation: String?): String {

        val tmpEquation = currentEquation ?: clear()

        if (tmpEquation.length > INPUT_LIMIT) {
            throw IllegalStateException(EQUATION_TOO_LONG_ERROR_MSG)
        }

        return tmpEquation.plus(inputNumber)
    }

    override fun setInputComma(currentEquation: String?): String {

        if (currentEquation.isNullOrEmpty()) {
            return EMPTY_EQUATION_COMMA
        }

        if (currentEquation.length > INPUT_LIMIT) {
            throw IllegalStateException("Equation too long")
        }

        if (currentEquation.contains(COMMA_SYMBOL).not()) {
            return currentEquation.plus(COMMA_SYMBOL)
        }

        return currentEquation
    }

    private fun clear() = ""

    companion object {
        const val INPUT_LIMIT = 14
        const val EMPTY_EQUATION_COMMA = "0."
        const val COMMA_SYMBOL = "."
        const val EQUATION_TOO_LONG_ERROR_MSG = "Equation too long"
    }
}
