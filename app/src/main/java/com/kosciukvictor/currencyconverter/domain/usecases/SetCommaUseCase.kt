package com.kosciukvictor.currencyconverter.domain.usecases

import com.kosciukvictor.currencyconverter.domain.repositories.input.InputRepository

class SetCommaUseCase(private val inputRepository: InputRepository) :
    UseCase<String, String?>() {

    override suspend fun action(params: String?): String =
        inputRepository.setInputComma(params)
}