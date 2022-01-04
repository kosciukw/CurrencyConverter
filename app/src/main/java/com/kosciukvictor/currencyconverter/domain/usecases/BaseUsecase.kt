package com.kosciukvictor.currencyconverter.domain.usecases

import kotlinx.coroutines.*

abstract class UseCase<out Type, in Params> {

    abstract suspend fun action(params: Params): Type

    operator fun invoke(
        params: Params,
        scope: CoroutineScope,
        executionDispatcher: CoroutineDispatcher = Dispatchers.IO,
        onResult: (Result<Type>) -> Unit = {}
    ): Job {
       return scope.launch {
            val result = withContext(executionDispatcher) {
                runCatching { action(params) }
            }
            onResult(result)
        }
    }
}