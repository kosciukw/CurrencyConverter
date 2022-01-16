package spek.usecase

import com.kosciukvictor.currencyconverter.domain.repositories.input.InputRepository
import com.kosciukvictor.currencyconverter.domain.usecases.SetNumberUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object SetNumberUseCaseTest : Spek({

    Feature("Set input number use case") {

        val inputRepository: InputRepository = mockk(relaxed = true)
        val setNumberUseCase = SetNumberUseCase(inputRepository)
        val mockEquation = "12"
        val mockInput = 3

        Scenario("Set input number") {
            When("Perform set input number") {
                runBlocking {
                    setNumberUseCase.action(Pair(mockInput, mockEquation))
                }
            }
            Then("Should invoke setNumberInput") {
                coVerify { inputRepository.setNumberInput(mockInput, mockEquation) }
            }
        }
    }
})