package spek.viewmodel

import com.kosciukvictor.currencyconverter.domain.api.models.ApiRates
import com.kosciukvictor.currencyconverter.domain.usecases.*
import com.kosciukvictor.currencyconverter.domain.utils.KEY_FROM
import com.kosciukvictor.currencyconverter.domain.utils.KEY_TO
import com.kosciukvictor.currencyconverter.domain.viewmodels.MainViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Job
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import org.amshove.kluent.*
import spek.utils.initializeArchTaskExecutor
import spek.utils.mock
import kotlin.test.assertTrue

object MainViewModelTest : Spek({

    initializeArchTaskExecutor()
    Feature("Main View Model") {
        val convertUseCase: ConvertUseCase = mockk(relaxed = true)
        val clearUseCase: ClearUseCase = mockk(relaxed = true)
        val setCommaUseCase: SetCommaUseCase = mockk(relaxed = true)
        val setNumberUseCase: SetNumberUseCase = mockk(relaxed = true)
        val getRatesUseCase: GetRatesUseCase = mockk(relaxed = true)
        val removeLastInputUseCase: RemoveLastInputUseCase = mockk(relaxed = true)
        val getCurrencyPreferencesUseCase: GetCurrencyPreferencesUseCase = mockk(relaxed = true)
        val saveCurrencyPreferencesUseCase: SaveCurrencyPreferencesUseCase = mockk(relaxed = true)

        val job: Job = mockk(relaxed = true)

        val viewModel by memoized {
            MainViewModel(
                clearUseCase = clearUseCase,
                convertUseCase = convertUseCase,
                setCommaUseCase = setCommaUseCase,
                setNumberUseCase = setNumberUseCase,
                getRatesUseCase = getRatesUseCase,
                removeLastInputUseCase = removeLastInputUseCase,
                getCurrencyPreferencesUseCase = getCurrencyPreferencesUseCase,
                saveCurrencyPreferencesUseCase = saveCurrencyPreferencesUseCase
            )
        }

        //get rates
        val ratesMapMock = mapOf("EUR" to 1.0, "USD" to 1.18147, "PLN" to 4.545281)
        fun prepareGetRatesUseCase(
            success: Boolean = true,
            timestamp: Double = 123.0,
            base: String = "EUR",
            date: String = "21-03-1998",
            rates: Map<String, Double> = ratesMapMock
        ) {
            coEvery {
                getRatesUseCase(
                    params = Unit,
                    executionDispatcher = any(),
                    onResult = any(),
                    scope = any()
                )
            } coAnswers  {
                lastArg<(Result<ApiRates>) -> Job>()(
                    when (success) {
                        true -> Result.success(
                            ApiRates.mock(
                                success = success,
                                timestamp = timestamp,
                                base = base,
                                date = date,
                                rates = rates
                            )
                        )
                        else ->
                            Result.failure(Throwable("Failed to retrieve data"))
                    }
                )
                job
            }
        }

        Scenario("Get rates from api") {
            Given("Get rates use case") {
                prepareGetRatesUseCase(success = true)
            }
            When("Get rates from api called") { viewModel.getApiRates() }
            Then("Retrieved data should be equal to api data") {
                viewModel.rates.value shouldBeEqualTo ApiRates.mock(true)
            }
        }

        Scenario("Handle get rates from api exception") {
            Given("Get rates use case") {
                prepareGetRatesUseCase(success = false)
            }
            When("Get rates from api called") { viewModel.getApiRates() }
            Then("Exception message should be Failed to retrieve data") {
                viewModel.exception.value shouldBeEqualTo "Failed to retrieve data"
            }
        }

        //get spinner prefs
        val getPrefsMapMock = mapOf(KEY_FROM to 0, KEY_TO to 1)
        fun prepareGetCurrencyPreferencesUseCase(
            success: Boolean = true,
            mapArgument: Map<String, Int> = getPrefsMapMock
        ) {
            coEvery {
                getCurrencyPreferencesUseCase(
                    params = Unit,
                    executionDispatcher = any(),
                    onResult = any(),
                    scope = any()
                )
            } coAnswers {
                lastArg<(Result<Map<String, Int>>) -> Job>()(
                    when (success) {
                        true ->
                            Result.success(
                                mapArgument
                            )
                        else ->
                            Result.failure(Throwable("Something went wrong"))
                    }
                )
                job
            }
        }

        Scenario("Handle get currency preferences") {
            Given("Get Currency Preferences UseCase") {
                prepareGetCurrencyPreferencesUseCase(success = true)
            }
            When("Get Currency Preferences UseCase called") { viewModel.getCurrencyPreferences() }
            Then("Retrieved prefs should be EUR") {
                viewModel.prefCurr.value shouldBeEqualTo getPrefsMapMock
            }
        }

        Scenario("Handle get spinner prefs exception") {
            Given("Get Currency Preferences UseCase") {
                prepareGetCurrencyPreferencesUseCase(success = false)
            }
            When("Get Currency Preferences UseCase called") { viewModel.getCurrencyPreferences() }
            Then("Exception thrown") {
                viewModel.exception.value shouldBeEqualTo "Something went wrong"
            }
        }

        // set spinner prefs
        fun prepareSaveCurrencyPreferencesUseCase(success: Boolean = true) {
            coEvery {
                saveCurrencyPreferencesUseCase(
                    params =  Pair(KEY_FROM, 3),
                    executionDispatcher = any(),
                    onResult = any(),
                    scope = any()
                )
            } coAnswers {
                lastArg<(Result<Unit>) -> Job>()(
                    when (success) {
                        true ->
                            Result.success(
                                Unit
                            )
                        else ->
                            Result.failure(Throwable("Something went wrong"))
                    }
                )
                job
            }
        }
        val setPrefsMapMock = mapOf(KEY_FROM to 3, KEY_TO to 1)
        val setPrefsPairMock: Pair<String, Int> = Pair(KEY_FROM, 3)
        Scenario("Handle set spinner prefs") {

            Given("Prepare Save Currency Preferences UseCase") {
                prepareSaveCurrencyPreferencesUseCase(success = true)
            }
            Given("Prepare Get Currency Preferences UseCase") {
                prepareGetCurrencyPreferencesUseCase(success = true, mapArgument = setPrefsMapMock)
            }
            When("Save Currency Preferences UseCase") {
                viewModel.saveCurrencyPreferences(
                    setPrefsPairMock.first,
                    setPrefsPairMock.second
                )
            }
            When("Get spinner prefs called") { viewModel.getCurrencyPreferences() }
            Then("Retrieved prefs should be equal to") {
                viewModel.prefCurr.value shouldBeEqualTo setPrefsMapMock
            }
        }

        Scenario("Handle SaveCurrencyPreferencesUseCase exception") {
            Given("Prepare SaveCurrencyPreferencesUseCase") {
                prepareSaveCurrencyPreferencesUseCase(success = false)
            }
            When("SaveCurrencyPreferencesUseCase called") {
                viewModel.saveCurrencyPreferences(
                    setPrefsPairMock.first,
                    setPrefsPairMock.second
                )
            }
            Then("Exception thrown") {
                viewModel.exception.value shouldBeEqualTo "Something went wrong"
            }
        }

        // set numerical input
        fun prepareSetNumericalInput(
            success: Boolean = true,
            nrInput: Int = 0,
            mockEquation: String = "12",
            mockOutcome: String = "120"
        ) {
            coEvery {
                setNumberUseCase(
                    params = Pair(nrInput, mockEquation),
                    executionDispatcher = any(),
                    onResult = any(),
                    scope = any()
                )
            } coAnswers {
                lastArg<(Result<String>) -> Job>()(
                    when (success) {
                        true ->
                            Result.success(
                                mockOutcome
                            )
                        else ->
                            Result.failure(Throwable("Something went wrong"))
                    }
                )
                job
            }
        }

        Scenario("Handle set numerical input") {
            val input = 3
            val mockEquation = "12"
            val mockOutcome = "123"
            Given("Prepare set numerical input") {
                prepareSetNumericalInput(
                    success = true,
                    nrInput = input,
                    mockEquation = mockEquation,
                    mockOutcome = mockOutcome
                )
            }
            When("Set numerical input called") { viewModel.setNumericalInput(input, mockEquation) }
            Then("Input equation should be equal to") {
                viewModel.inputEquation.value shouldBeEqualTo mockOutcome
            }
        }

        // set input comma
        fun prepareSetInputComma(
            success: Boolean = true,
            mockEquation: String = "12",
            mockOutcome: String = "12."
        ) {
            coEvery {
                setCommaUseCase(
                    params = mockEquation,
                    executionDispatcher = any(),
                    onResult = any(),
                    scope = any()
                )
            } coAnswers {
                lastArg<(Result<String>) -> Job>()(
                    when (success) {
                        true ->
                            Result.success(
                                mockOutcome
                            )
                        else ->
                            Result.failure(Throwable("Something went wrong"))
                    }
                )
                job
            }
        }

        Scenario("Handle set comma input") {
            val mockEquation = "12"
            val mockOutcome = "12."
            Given("Prepare set comma input") {
                prepareSetInputComma(
                    success = true
                )
            }
            When("Set comma input called") { viewModel.setInputComma(mockEquation) }
            Then("Input equation should be equal to") {
                viewModel.inputEquation.value shouldBeEqualTo mockOutcome
            }
        }

        // clear values
        fun prepareClearValues(
            success: Boolean = true
        ) {
            coEvery {
                clearUseCase(
                    params = Unit,
                    executionDispatcher = any(),
                    onResult = any(),
                    scope = any()
                )
            } coAnswers {
                lastArg<(Result<String>) -> Job>()(
                    when (success) {
                        true ->
                            Result.success(
                                ""
                            )
                        else ->
                            Result.failure(Throwable("Something went wrong"))
                    }
                )
                job
            }
        }

        Scenario("Handle clear input") {
            Given("Prepare clear input") {
                prepareClearValues(
                    success = true
                )
            }
            When("Clear input called") { viewModel.clear() }
            Then("Input equation should be empty") {
                assertTrue { viewModel.inputEquation.value!!.isEmpty() }
            }
        }

        // convert values
        fun prepareConvertValues(
            success: Boolean = true,
            prefsMap: Map<String, Int>? = getPrefsMapMock,
            rates: Map<String, Double>? = ratesMapMock,
            mockEquation: String = "1",
            mockOutcome: String = "1.18147"
        ) {
            coEvery {
                convertUseCase(
                    params = Triple(prefsMap, rates, mockEquation),
                    executionDispatcher = any(),
                    onResult = any(),
                    scope = any()
                )
            } coAnswers {
                lastArg<(Result<String>) -> Job>()(
                    when (success) {
                        true ->
                            Result.success(
                                mockOutcome
                            )
                        else ->
                            Result.failure(Throwable("Something went wrong"))
                    }
                )
                job
            }
        }

        Scenario("Handle convert values") {
            val prefsMap = getPrefsMapMock
            val ratesMap = ratesMapMock
            val mockEquation = "1"
            val mockOutcome = "1.18147"

            Given("Prepare convert use case") {
                prepareConvertValues(
                    success = true,
                    rates = ratesMap,
                    prefsMap = prefsMap,
                    mockEquation = mockEquation,
                    mockOutcome = mockOutcome
                )
            }
            When("Convert use case called") {
                viewModel.convertValues(
                    prefsMap,
                    ratesMap,
                    mockEquation
                )
            }
            Then("Outcome equation should be equal to") {
                viewModel.outputEquation.value shouldBeEqualTo mockOutcome
            }
        }



        Scenario("Handle convert values exception") {
            val prefsMap = getPrefsMapMock
            val ratesMap = ratesMapMock
            val mockEquation = "1"

            Given("Prepare convert use case") {
                prepareConvertValues(
                    success = false
                )
            }
            When("Convert use case called") {
                viewModel.convertValues(
                    prefsMap,
                    ratesMap,
                    mockEquation
                )
            }
            Then("Outcome equation should be equal to") {
                viewModel.exception.value shouldBeEqualTo "Something went wrong"
            }
        }
    }
})