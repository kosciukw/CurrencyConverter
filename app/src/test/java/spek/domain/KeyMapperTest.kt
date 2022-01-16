package spek.domain

import com.kosciukvictor.currencyconverter.domain.utils.KEY_FROM
import com.kosciukvictor.currencyconverter.domain.utils.KEY_TO
import com.kosciukvictor.currencyconverter.domain.utils.keymapper.KeyMapper
import com.kosciukvictor.currencyconverter.domain.utils.keymapper.KeyMapperImpl

import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object KeyMapperTest : Spek({

    Feature("Key mapper spek"){


        val keyMapper : KeyMapper by memoized {
            KeyMapperImpl()
        }

        Scenario("Map key From spek"){
            //given
            val expectedValue = "EUR"
            val ratesIndexMapMock: Map<String, Int> = mapOf(KEY_FROM to 0, KEY_TO to 1)
            val ratesMapMock: Map<String, Double> = mapOf("EUR" to 1.0, "USD" to 1.0)

            When("Call map key From"){
                keyMapper.mapFrom(ratesIndexMapMock, ratesMapMock)
            }
            Then("Expected value should be equal to"){
                expectedValue `should be equal to` "EUR"
            }
        }

        Scenario("Map key To spek"){
            //given
            val expectedValue = "USD"
            val ratesIndexMapMock: Map<String, Int> = mapOf(KEY_FROM to 0, KEY_TO to 1)
            val ratesMapMock: Map<String, Double> = mapOf("EUR" to 1.0, "USD" to 1.0)

            When("Call map key To"){
                keyMapper.mapTo(ratesIndexMapMock, ratesMapMock)
            }
            Then("Expected value should be equal to"){
                expectedValue `should be equal to` "USD"
            }
        }
    }
})