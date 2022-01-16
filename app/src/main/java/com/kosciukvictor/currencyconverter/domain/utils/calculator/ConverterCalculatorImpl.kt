package com.kosciukvictor.currencyconverter.domain.utils.calculator

import kotlin.math.round

class ConverterCalculatorImpl : ConverterCalculator {

    override fun calculate(from: Double, to: Double, value : Double): Double {
        var result = value
        result = result * to / from
        result = round(result * 100) / 100
        return result
    }
}