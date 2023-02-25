package com.gph.tst.giphytestapp.common

sealed class Resource<out S, out E> {
    data class Success<out S>(val success: S) : Resource<S, Nothing>()
    data class Failure<out E>(val cause: E) : Resource<Nothing, E>()
}

inline fun <T, S, E> Resource<S, E>.flatMap(transform: (S) -> Resource<T, E>): Resource<T, E> {
    return when (this) {
        is Resource.Success -> transform(success)
        is Resource.Failure -> this
    }
}

fun <T, S, E> Resource<S, E>.map(transform: (S) -> (T)): Resource<T, E> {
    return when (this) {
        is Resource.Success -> Resource.Success(transform(success))
        is Resource.Failure -> this
    }
}

inline fun <S, E> Resource<S, E>.onFailure(block: (Resource.Failure<E>) -> Nothing): S {
    return when(this) {
        is Resource.Success -> this.success
        is Resource.Failure -> block(this)
    }
}

inline fun <S, E> Resource<S, E>.onResult(
    onSuccess: (Resource.Success<S>) -> Unit,
    onFailure: (Resource.Failure<E>) -> Unit
) {
    when(this) {
        is Resource.Success -> onSuccess(this)
        is Resource.Failure -> onFailure(this)
    }
}