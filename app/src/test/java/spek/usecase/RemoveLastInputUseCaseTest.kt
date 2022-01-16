package spek.usecase

import com.kosciukvictor.currencyconverter.domain.repositories.input.InputRepository
import com.kosciukvictor.currencyconverter.domain.usecases.RemoveLastInputUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object RemoveLastInputUseCaseTest : Spek({

    Feature("Remove last input use case") {

        val inputRepository: InputRepository = mockk(relaxed = true)
        val removeLastInputUseCase = RemoveLastInputUseCase(inputRepository)
        val mockEquation = "12"

        Scenario("Remove last input") {
            When("Perform remove last input") {
                runBlocking {
                    removeLastInputUseCase.action(mockEquation)
                }
            }
            Then("Should invoke setNumberInput") {
                coVerify { inputRepository.removeLastInput(mockEquation) }
            }
        }
    }
})