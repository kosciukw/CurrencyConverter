package spek.usecase

import com.kosciukvictor.currencyconverter.domain.usecases.ConvertUseCase
import com.kosciukvictor.currencyconverter.domain.utils.KEY_FROM
import com.kosciukvictor.currencyconverter.domain.utils.KEY_TO
import com.kosciukvictor.currencyconverter.domain.utils.converter.CurrencyConverter
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object ConvertUseCaseTest : Spek({

    Feature("Convert use case") {

        val converter: CurrencyConverter = mockk(relaxed = true)
        val setNumberUseCase = ConvertUseCase(converter)
        val ratesIndexMapMock = mapOf(KEY_FROM to 0, KEY_TO to 1)
        val ratesMapMock = mapOf("EUR" to 1.0, "USD" to 1.18147, "PLN" to 4.545281)
        val mockEquation = "12"

        Scenario("Set input number") {
            When("Perform set input number") {
                runBlocking {
                    setNumberUseCase.action(
                        Triple(
                            ratesIndexMapMock,
                            ratesMapMock,
                            mockEquation
                        )
                    )
                }
            }
            Then("Should invoke setNumberInput") {
                coVerify {
                    converter.convertCurrencies(
                        ratesIndexMap = ratesIndexMapMock,
                        ratesValuesMap = ratesMapMock,
                        currentEquation = mockEquation
                    )
                }
            }
        }
    }
})