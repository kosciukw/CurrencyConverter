package spek.usecase

import com.kosciukvictor.currencyconverter.domain.repositories.preferences.CurrencyPreferencesRepository
import com.kosciukvictor.currencyconverter.domain.usecases.SaveCurrencyPreferencesUseCase
import com.kosciukvictor.currencyconverter.domain.utils.KEY_FROM
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object SaveCurrencyPreferencesUseCaseTest : Spek({

    Feature("SaveCurrencyPreferencesUseCaseTest") {

        val currencyPreferencesRepository: CurrencyPreferencesRepository = mockk(relaxed = true)
        val setRatesPreferencesUseCase = SaveCurrencyPreferencesUseCase(currencyPreferencesRepository)
        val mockPosition = 3
        val mockKey = KEY_FROM

        Scenario("Save Currency Position"){
            When("Perform saveCurrencyPosition"){
                runBlocking {
                    setRatesPreferencesUseCase.action(Pair(mockKey, mockPosition))
                }
            }
            Then("Should invoke saveCurrencyPosition"){
                coVerify { currencyPreferencesRepository.saveCurrencyPosition(mockKey, mockPosition) }
            }
        }
    }
})