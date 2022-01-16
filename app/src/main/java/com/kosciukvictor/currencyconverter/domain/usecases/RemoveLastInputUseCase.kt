package com.kosciukvictor.currencyconverter.domain.usecases

import com.kosciukvictor.currencyconverter.domain.repositories.input.InputRepository

class RemoveLastInputUseCase(private val inputRepository: InputRepository) :
    UseCase<String, String?>() {

    override suspend fun action(params: String?): String =
        inputRepository.removeLastInput(params)
}