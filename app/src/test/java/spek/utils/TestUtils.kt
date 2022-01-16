package spek.utils

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import com.kosciukvictor.currencyconverter.domain.api.models.ApiRates
import org.spekframework.spek2.dsl.LifecycleAware

internal fun ApiRates.Companion.mock(
    success: Boolean = true,
    timestamp: Double = 123.0,
    base: String = "EUR",
    date: String = "21-03-1998",
    rates: Map<String, Double> = mapOf("EUR" to 1.0, "USD" to 1.18147, "PLN" to 4.545281)
) = ApiRates(
    success = success,
    timestamp = timestamp,
    base = base,
    date = date,
    rates = rates
)

fun LifecycleAware.initializeArchTaskExecutor() {
    beforeEachTest {
        ArchTaskExecutor.getInstance().setDelegate(TestArchTaskExecutor())
    }
    afterEachTest {
        ArchTaskExecutor.getInstance().setDelegate(null)
    }
}

class TestArchTaskExecutor : TaskExecutor() {
    override fun executeOnDiskIO(runnable: Runnable) = runnable.run()

    override fun isMainThread(): Boolean = true

    override fun postToMainThread(runnable: Runnable) = runnable.run()
}