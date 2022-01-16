package spek.usecase

import com.kosciukvictor.currencyconverter.domain.repositories.input.InputRepository
import com.kosciukvictor.currencyconverter.domain.usecases.ClearUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object ClearUseCaseTest : Spek({

    Feature("Clear input use case") {
        val inputRepository: InputRepository = mockk(relaxed = true)
        val clearInputUseCase = ClearUseCase(inputRepository)

        Scenario("Clear input") {
            When("Perform clear input") {
                runBlocking {
                    clearInputUseCase.action(Unit)
                }
            }
            Then("Should invoke clear input") {
                coVerify { inputRepository.clear() }
            }
        }
    }
})