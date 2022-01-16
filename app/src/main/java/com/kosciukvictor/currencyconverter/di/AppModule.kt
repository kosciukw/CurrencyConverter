package com.kosciukvictor.currencyconverter.di


import com.kosciukvictor.currencyconverter.domain.repositories.input.InputRepository
import com.kosciukvictor.currencyconverter.domain.repositories.input.InputRepositoryImpl
import com.kosciukvictor.currencyconverter.domain.usecases.*
import com.kosciukvictor.currencyconverter.domain.utils.calculator.ConverterCalculator
import com.kosciukvictor.currencyconverter.domain.utils.calculator.ConverterCalculatorImpl
import com.kosciukvictor.currencyconverter.domain.utils.converter.CurrencyConverter
import com.kosciukvictor.currencyconverter.domain.utils.converter.CurrencyConverterImpl
import com.kosciukvictor.currencyconverter.domain.utils.formatter.CurrencyFormatter
import com.kosciukvictor.currencyconverter.domain.utils.formatter.CurrencyFormatterImpl
import com.kosciukvictor.currencyconverter.domain.utils.keymapper.KeyMapper
import com.kosciukvictor.currencyconverter.domain.utils.keymapper.KeyMapperImpl
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
    factory { ConvertUseCase(currencyConverter = get()) }
    factory { GetRatesUseCase(currencyExchangeService = get()) }
    factory { GetCurrencyPreferencesUseCase( currencyPreferencesRepository = get()) }
    factory { SaveCurrencyPreferencesUseCase(currencyPreferencesRepository = get()) }

    factory<CurrencyConverter> {
        CurrencyConverterImpl(
            formatter = get(),
            calculator = get(),
            keyMapper = get()
        )
    }
    single<CurrencyFormatter> { CurrencyFormatterImpl() }
    single<KeyMapper> { KeyMapperImpl() }
    single<ConverterCalculator> { ConverterCalculatorImpl() }

    viewModel {
        MainViewModel(
            clearUseCase = get(),
            setCommaUseCase = get(),
            setNumberUseCase = get(),
            removeLastInputUseCase = get(),
            convertUseCase = get(),
            getRatesUseCase = get(),
            getCurrencyPreferencesUseCase = get(),
            saveCurrencyPreferencesUseCase = get()
        )
    }
}