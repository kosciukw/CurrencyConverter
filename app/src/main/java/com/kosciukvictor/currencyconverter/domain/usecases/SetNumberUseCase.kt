package com.kosciukvictor.currencyconverter.domain.usecases

import com.kosciukvictor.currencyconverter.domain.repositories.input.InputRepository

class SetNumberUseCase(
    private val inputRepository: InputRepository
) : UseCase<String, Pair<Int, String?>>() {

    override suspend fun action(params: Pair<Int, String?>): String =
        inputRepository.setNumberInput(params.first, params.second)
}