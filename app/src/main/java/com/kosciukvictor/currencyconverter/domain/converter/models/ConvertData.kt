package com.kosciukvictor.currencyconverter.domain.converter.models

class ConvertData(
    val ratesIndexMap : Map<String, Int>?,
    val ratesValuesMap: Map<String, Double>?,
    val currentEquation: String?
)