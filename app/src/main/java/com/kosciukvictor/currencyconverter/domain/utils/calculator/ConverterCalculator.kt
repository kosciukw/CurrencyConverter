package com.kosciukvictor.currencyconverter.domain.utils.calculator

interface ConverterCalculator {
    fun calculate(from: Double, to: Double, value : Double) : Double
}