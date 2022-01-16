package com.kosciukvictor.currencyconverter.di


import com.kosciukvictor.currencyconverter.domain.repositories.input.InputRepository
import com.kosciukvictor.currencyconverter.domain.repositories.input.InputRepositoryImpl
import com.kosciukvictor.currencyconverter.domain.usecases.*
import com.kosciukvictor.currencyconverter.domain.viewmodels.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val appModule = module {

    factory<InputRepository> { InputRepositoryImpl() }
    factory { SetNumberUseCase(inputRepository = get()) }
    factory { ClearUseCase(inputRepository = get()) }
    factory { SetCommaUseCase(inputRepository = get()) }
    factory { RemoveLastInputUseCase(inputRepository = get()) }


    viewModel {
        MainViewModel(
            clearUseCase = get(),
            setCommaUseCase = get(),
            setNumberUseCase = get(),
            removeLastInputUseCase = get()
        )
    }
}