package spek.usecase

import com.kosciukvictor.currencyconverter.domain.repositories.input.InputRepository
import com.kosciukvictor.currencyconverter.domain.usecases.SetCommaUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

class SetCommaUseCaseTest : Spek({

    Feature("Set comma input use case") {
        val inputRepository: InputRepository = mockk(relaxed = true)
        val setCommaUseCase = SetCommaUseCase(inputRepository)
        val mockEquation = "12"

        Scenario("Set input comma") {
            When("Perform set input comma") {
                runBlocking {
                    setCommaUseCase.action(mockEquation)
                }
            }
            Then("Should invoke set comma input") {
                coVerify { inputRepository.setInputComma(mockEquation) }
            }
        }
    }
})