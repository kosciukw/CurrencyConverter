package com.kosciukvictor.currencyconverter.domain.usecases

import com.kosciukvictor.currencyconverter.domain.repositories.input.InputRepository

class ClearUseCase(private val inputRepository: InputRepository) :
    UseCase<String, Unit>() {


    override suspend fun action(params: Unit): String =
        inputRepository.clear()
}