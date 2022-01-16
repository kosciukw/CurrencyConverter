package spek.usecase

import com.kosciukvictor.currencyconverter.domain.api.CurrencyExchangeService
import com.kosciukvictor.currencyconverter.domain.usecases.GetRatesUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

@ExperimentalCoroutinesApi
object GetRatesUseCaseTest : Spek({

    Feature("Get rates use case") {

        val currencyExchangeService: CurrencyExchangeService = mockk(relaxed = true)
        val getRatesUseCase = GetRatesUseCase(currencyExchangeService)

        Scenario("Get current rates from api") {
            When("Perform get current rates from api") {
                runBlocking {
                    getRatesUseCase.action(Unit)
                }
            }
            Then("Should provide correct result to repository") {
               coVerify { currencyExchangeService.getLatest()  }
            }
        }
    }
})