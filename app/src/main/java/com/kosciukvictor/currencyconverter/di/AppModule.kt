package com.kosciukvictor.currencyconverter.di


import com.kosciukvictor.currencyconverter.domain.viewmodels.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val appModule = module {

    viewModel {
        MainViewModel(
        )
    }
}