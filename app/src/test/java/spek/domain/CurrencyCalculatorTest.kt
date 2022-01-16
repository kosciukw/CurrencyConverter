package spek.domain

import com.kosciukvictor.currencyconverter.domain.utils.calculator.ConverterCalculator
import com.kosciukvictor.currencyconverter.domain.utils.calculator.ConverterCalculatorImpl
import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object CurrencyCalculatorTest : Spek({

    Feature("Currency Calculator Test") {

        val converterCalculator: ConverterCalculator by memoized {
            ConverterCalculatorImpl()
        }

        Scenario("Convert EUR to USD"){
            //given
            val from = 1.0
            val to = 1.18
            val valueToConvert = 10.0
            val expectedValue = 11.8

            var convertedValue = 0.0

            When("Convert EUR to USD"){
                convertedValue =
                    converterCalculator.calculate(from, to, valueToConvert)
            }
            Then("Converted value should be expected value"){
                convertedValue `should be equal to` expectedValue
            }
        }

        Scenario("Convert USD to EUR"){
            //given
            val from = 1.18
            val to = 1.0
            val valueToConvert = 10.0
            val expectedValue = 8.47

            var convertedValue = 0.0

            When("Convert USD to EUR"){
                convertedValue =
                    converterCalculator.calculate(from, to, valueToConvert)
            }
            Then("Converted value should be expected value"){
                convertedValue `should be equal to` expectedValue
            }
        }

    }
})