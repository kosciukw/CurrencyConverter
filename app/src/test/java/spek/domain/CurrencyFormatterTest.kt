package spek.domain


import com.kosciukvictor.currencyconverter.domain.utils.formatter.CurrencyFormatter
import com.kosciukvictor.currencyconverter.domain.utils.formatter.CurrencyFormatterImpl
import org.amshove.kluent.shouldBeEqualTo
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object CurrencyFormatterTest : Spek({

    Feature("Currency Formatter Test"){

        val currencyFormatter : CurrencyFormatter by memoized {
            CurrencyFormatterImpl()
        }

        Scenario("Formatting zero"){
            //given
            val inputWithComma = 0.0
            val expectedValue : String = "0"
            lateinit var formattedValue : String

            When("Price to String called"){
                formattedValue =
                    currencyFormatter.priceToString(inputWithComma)
            }
            Then("Formatted value should equal to expected value"){
                formattedValue shouldBeEqualTo expectedValue
            }
        }

        Scenario("Formatting low floating value"){
            //given
            val inputWithComma = 9.34
            val expectedValue : String = "9,34"
            lateinit var formattedValue : String

            When("Price to String called"){
                formattedValue =
                    currencyFormatter.priceToString(inputWithComma)
            }
            Then("Formatted value should equal to expected value"){
                formattedValue shouldBeEqualTo expectedValue
            }
        }

        Scenario("Formatting high floating value"){
            //given
            val inputWithComma = 1999.34
            val expectedValue : String = "1 999,34"
            lateinit var formattedValue : String

            When("Price to String called"){
                formattedValue =
                    currencyFormatter.priceToString(inputWithComma)
            }
            Then("Formatted value should equal to expected value"){
                formattedValue shouldBeEqualTo expectedValue
            }
        }

        Scenario("Formatting low fixed value"){
            //given
            val input : Double = 99.0
            val expectedValue : String = "99"
            lateinit var formattedValue : String

            When("Price to String called"){
                formattedValue =
                    currencyFormatter.priceToString(input)
            }
            Then("Formatted value should equal to expected value"){
                formattedValue shouldBeEqualTo expectedValue
            }
        }

        Scenario("Formatting high fixed input"){
            //given
            val input : Double = 1999.0
            val expectedValue : String = "1 999"
            lateinit var formattedValue : String

            When("Price to String called"){
                formattedValue =
                    currencyFormatter.priceToString(input)
            }
            Then("Formatted value should equal to expected value"){
                formattedValue shouldBeEqualTo expectedValue
            }
        }

        Scenario("Formatting very high fixed input"){
            //given
            val input : Double = 1999999.0
            val expectedValue : String = "1 999 999"
            lateinit var formattedValue : String

            When("Price to String called"){
                formattedValue =
                    currencyFormatter.priceToString(input)
            }
            Then("Formatted value should equal to expected value"){
                formattedValue shouldBeEqualTo expectedValue
            }
        }

        Scenario("Formatting very high floating input"){
            //given
            val input : Double = 1999999.34
            val expectedValue : String = "1 999 999,34"
            lateinit var formattedValue : String

            When("Price to String called"){
                formattedValue =
                    currencyFormatter.priceToString(input)
            }
            Then("Formatted value should equal to expected value"){
                formattedValue shouldBeEqualTo expectedValue
            }
        }

    }
})
