package com.kosciukvictor.currencyconverter.domain.usecases

import com.kosciukvictor.currencyconverter.domain.repositories.input.InputRepository

class ClearEquationUseCase(
    private val inputRepository: InputRepository
) : UseCase<String, Unit>() {

    override suspend fun action(params: Unit): String {
        return inputRepository.clearEquation()
    }
}