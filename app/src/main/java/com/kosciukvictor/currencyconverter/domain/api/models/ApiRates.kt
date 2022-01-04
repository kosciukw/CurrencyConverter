package com.kosciukvictor.currencyconverter.domain.api.models

data class ApiRates(
    val success : Boolean,
    val timestamp : Double,
    val base: String,
    val date : String,
    val rates : Map<String, Double>
){
    companion object{

    }
}