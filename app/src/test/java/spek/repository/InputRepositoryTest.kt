package spek.repository

import com.kosciukvictor.currencyconverter.domain.repositories.input.InputRepository
import com.kosciukvictor.currencyconverter.domain.repositories.input.InputRepositoryImpl


import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import java.lang.IllegalStateException
import kotlin.test.assertFailsWith


@ExperimentalCoroutinesApi
object InputRepositoryTest : Spek({

    Feature("InputRepository") {

        val repository: InputRepository by memoized {
            InputRepositoryImpl()
        }

        Scenario("Set numerical input to non-empty equation") {
            //given
            val numberToInput = 3
            var equationToExtend = "12"

            When("SetNumberInput to non-empty equation") {
                equationToExtend =
                    runBlocking { repository.setNumberInput(numberToInput, equationToExtend) }
            }
            Then("New element added to equation") {
                equationToExtend `should be equal to` "123"
            }
        }

        Scenario("Set numerical input to empty equation") {
            //given
            val numberToInput = 1
            var equationToExtend = ""

            When("SetNumberInput to empty equation") {
                equationToExtend =
                    runBlocking { repository.setNumberInput(numberToInput, equationToExtend) }
            }
            Then("New element added to equation") {
                equationToExtend `should be equal to` "1"
            }
        }


        Scenario("Should throw exception when too long input") {
            //given
            val numberToInput = 1
            val equationToExtend = "123456789101112"
            lateinit var expectedException: Exception

            When("Set number input with too long input") {
                expectedException = assertFailsWith {
                    repository.setNumberInput(
                        numberToInput,
                        equationToExtend
                    )
                }
            }
            Then("Throws Illegal state exception") {
                expectedException is IllegalStateException
            }
        }


        Scenario("Set comma to empty equation") {
            //given
            var equationToExtend = ""

            When("Addresses are being obtained") {
                equationToExtend =
                    runBlocking { repository.setInputComma(equationToExtend) }
            }
            Then("Should obtain addresses from cache") {
                equationToExtend `should be equal to` "0."
            }
        }

        Scenario("Set comma to 0") {
            //given
            var equationToExtend = "0"

            When("Addresses are being obtained") {
                equationToExtend =
                    runBlocking { repository.setInputComma(equationToExtend) }
            }
            Then("Should obtain addresses from cache") {
                equationToExtend `should be equal to` "0."
            }
        }

        Scenario("Set comma to non-empty equation") {
            //given
            var equationToExtend = "1"

            When("Addresses are being obtained") {
                equationToExtend =
                    runBlocking { repository.setInputComma(equationToExtend) }
            }
            Then("Should obtain addresses from cache") {
                equationToExtend `should be equal to` "1."
            }
        }

        Scenario("Set comma to empty equation") {
            //given
            var equationToExtend = ""

            When("Addresses are being obtained") {
                equationToExtend =
                    runBlocking { repository.setInputComma(equationToExtend) }
            }
            Then("Should obtain addresses from cache") {
                equationToExtend `should be equal to` "0."
            }
        }

        Scenario("Should not place second comma") {
            //given
            var equationToExtend = "1."

            When("Addresses are being obtained") {
                equationToExtend =
                    runBlocking { repository.setInputComma(equationToExtend) }
            }
            Then("Should obtain addresses from cache") {
                equationToExtend `should be equal to` "1."
            }
        }

        Scenario("Remove last input to empty equation") {
            //given
            var equationToExtend = ""

            When("Remove last input called") {
                equationToExtend =
                    runBlocking { repository.removeLastInput(equationToExtend) }
            }
            Then("Should obtain addresses from cache") {
                equationToExtend `should be equal to` ""
            }
        }


        Scenario("Remove last input to non-empty equation") {
            //given
            var equationToExtend = "11"

            When("Remove last input called") {
                equationToExtend =
                    runBlocking { repository.removeLastInput(equationToExtend) }
            }
            Then("Should obtain addresses from cache") {
                equationToExtend `should be equal to` "1"
            }
        }

        Scenario("Remove last input to a single character equation") {
            //given
            var equationToExtend = "1"

            When("Remove last input called") {
                equationToExtend =
                    runBlocking { repository.removeLastInput(equationToExtend) }
            }
            Then("Should obtain addresses from cache") {
                equationToExtend `should be equal to` ""
            }
        }

        Scenario("Remove last input to a very long equation equation") {
            //given
            var equationToExtend = "12345678911121314"

            When("Remove last input called") {
                equationToExtend =
                    runBlocking { repository.removeLastInput(equationToExtend) }
            }
            Then("Should clear equation") {
                equationToExtend `should be equal to` ""
            }
        }
    }
})