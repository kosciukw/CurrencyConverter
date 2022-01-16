package com.kosciukvictor.currencyconverter.domain.utils.formatter

interface CurrencyFormatter {
    fun priceToString(price: Double): String
}