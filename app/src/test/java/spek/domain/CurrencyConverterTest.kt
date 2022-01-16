package spek.domain

import com.kosciukvictor.currencyconverter.domain.utils.KEY_FROM
import com.kosciukvictor.currencyconverter.domain.utils.KEY_TO
import com.kosciukvictor.currencyconverter.domain.utils.calculator.ConverterCalculator
import com.kosciukvictor.currencyconverter.domain.utils.converter.CurrencyConverter
import com.kosciukvictor.currencyconverter.domain.utils.converter.CurrencyConverterImpl
import com.kosciukvictor.currencyconverter.domain.utils.formatter.CurrencyFormatter
import com.kosciukvictor.currencyconverter.domain.utils.keymapper.KeyMapper
import io.mockk.every
import io.mockk.mockk
import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import spek.utils.CurrencyFormatterFakeImpl
import java.lang.IllegalStateException
import kotlin.test.assertFailsWith

object CurrencyConverterTest : Spek({

    Feature("CurrencyConverterTest") {
        val currencyFormatter: CurrencyFormatter = CurrencyFormatterFakeImpl()
        val converterCalculator: ConverterCalculator = mockk(relaxed = true)
        val keyMapper: KeyMapper = mockk(relaxed = true)

        val currencyConverter: CurrencyConverter by memoized {
            CurrencyConverterImpl(
                calculator = converterCalculator,
                formatter = currencyFormatter,
                keyMapper = keyMapper
            )
        }

        fun prepareMapperFrom(
            currency: String = "EUR",
            ratesIndexMapMock: Map<String, Int> = mapOf(KEY_FROM to 0, KEY_TO to 1),
            ratesMapMock: Map<String, Double> = mapOf("EUR" to 1.0, "USD" to 1.0)
        ) {
            every { keyMapper.mapFrom(ratesIndexMapMock, ratesMapMock) } returns currency
        }

        fun prepareMapperTo(
            currency: String = "USD",
            ratesIndexMapMock: Map<String, Int> = mapOf(KEY_FROM to 0, KEY_TO to 1),
            ratesMapMock: Map<String, Double> = mapOf("EUR" to 1.0, "USD" to 1.0)
        ) {
            every { keyMapper.mapTo(ratesIndexMapMock, ratesMapMock) } returns currency
        }

        fun prepareConvertCalculations(
            from: Double = 1.0,
            to: Double = 1.18,
            value: Double = 1.0,
            expected: Double = 1.18
        ) {
            every { converterCalculator.calculate(from, to, value) } returns expected
        }

        Scenario("Convert calculations failure due to null keys") {
            lateinit var expectedException: Exception


            Given("Prepare map key To") {
                prepareMapperTo(
                    "USD",
                    mapOf(KEY_FROM to 0, KEY_TO to 1),
                    mapOf("EUR" to 1.0, "USD" to 1.18)
                )
            }
            Given("Prepare calculator") {
                prepareConvertCalculations(1.0, 1.18, 1.0, 1.18)
            }
            When("Convert calculations with null keys") {
                expectedException = assertFailsWith {
                    currencyConverter.convertCurrencies(
                        mapOf(KEY_FROM to 0, KEY_TO to 1),
                        mapOf("EUR" to 1.0, "USD" to 1.18),
                        "1"
                    )
                }
            }
            Then("Converted value should be") {
                expectedException is IllegalStateException
            }
        }

        Scenario("Convert calculations failure due to null rates") {
            lateinit var expectedException: Exception

            Given("Prepare map key From") {
                prepareMapperFrom(
                    "EUR",
                    mapOf(KEY_FROM to 0, KEY_TO to 1),
                    mapOf("EUR" to 1.0, "USD" to 1.18)
                )
            }
            Given("Prepare map key To") {
                prepareMapperTo(
                    "USD",
                    mapOf(KEY_FROM to 0, KEY_TO to 1),
                    mapOf("PLN" to 1.0, "GBP" to 1.18)
                )
            }
            Given("Prepare calculator") {
                prepareConvertCalculations(1.0, 1.18, 1.0, 1.18)
            }
            When("Convert calculations with null rates ") {
                expectedException = assertFailsWith {
                    currencyConverter.convertCurrencies(
                        mapOf(KEY_FROM to 0, KEY_TO to 1),
                        mapOf("PLN" to 1.0, "GBP" to 1.18),
                        "1"
                    )
                }
            }
            Then("Converted value should be") {
                expectedException is IllegalStateException
            }
        }

        Scenario("Convert calculations success") {
            lateinit var convertedValue: String

            Given("Prepare map key From") {
                prepareMapperFrom(
                    "EUR",
                    mapOf(KEY_FROM to 0, KEY_TO to 1),
                    mapOf("EUR" to 1.0, "USD" to 1.18)
                )
            }
            Given("Prepare map key To") {
                prepareMapperTo(
                    "USD",
                    mapOf(KEY_FROM to 0, KEY_TO to 1),
                    mapOf("EUR" to 1.0, "USD" to 1.18)
                )
            }
            Given("Prepare calculator") {
                prepareConvertCalculations(1.0, 1.18, 1.0, 1.18)
            }
            When("Convert calculations") {
                convertedValue = currencyConverter.convertCurrencies(
                    mapOf(KEY_FROM to 0, KEY_TO to 1),
                    mapOf("EUR" to 1.0, "USD" to 1.18),
                    "1"
                )
            }
            Then("Converted value should be") {
                convertedValue `should be equal to` "1.0 EUR = \n1.18 USD"
            }
        }
    }
})