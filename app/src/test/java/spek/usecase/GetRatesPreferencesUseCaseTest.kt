package spek.usecase

import com.kosciukvictor.currencyconverter.domain.repositories.preferences.CurrencyPreferencesRepository
import com.kosciukvictor.currencyconverter.domain.usecases.GetCurrencyPreferencesUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object GetRatesPreferencesUseCaseTest : Spek({

    Feature("Get currency preferences use case") {

        val currencyPreferencesRepository: CurrencyPreferencesRepository = mockk(relaxed = true)
        val getRatesPreferences = GetCurrencyPreferencesUseCase(currencyPreferencesRepository)


        Scenario("Get currency preferences"){
            When("Perform get currency preferences"){
                runBlocking {
                    getRatesPreferences.action(Unit)
                }
            }
            Then("Should provide correct result to repository"){
                coVerify { currencyPreferencesRepository.getCurrencyPreferences() }
            }
        }
    }
})