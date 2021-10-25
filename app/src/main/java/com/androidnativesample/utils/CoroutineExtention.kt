package com.androidnativesample.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T : Any> runIO(work: suspend () -> T): ResultHandler<T> = withContext(Dispatchers.IO) {
    try {
        val result = work()
        withContext(Dispatchers.Main) {
            when (result) {
                is Throwable -> ResultHandler.Error(result)
                else -> ResultHandler.Success(result)
            }
        }
    } catch (ex: Exception) {
        withContext(Dispatchers.Main) {
            ResultHandler.Error(ex)
        }
    }
}

sealed class ResultHandler<out T : Any> {
    data class Success<out T : Any>(val data: T) : ResultHandler<T>()
    data class Error(val throwable: Throwable) : ResultHandler<Nothing>()
}