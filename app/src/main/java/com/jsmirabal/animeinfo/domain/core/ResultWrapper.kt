package com.jsmirabal.animeinfo.domain.core

import java.util.Objects

sealed class ResultWrapper<out S, out E> {

    class Success<S>(private val value: S) : ResultWrapper<S, Nothing>() {
        fun get() = value
    }

    class Error<E>(private val value: E) : ResultWrapper<Nothing, E>() {
        fun get() = value
    }

    fun success(): Success<out S>? = this as? Success

    fun error(): Error<out E>? = this as? Error

    fun successOrError(success: (S) -> Unit, error: (E) -> Unit) {
        when(this) {
            is Success -> success(get())
            is Error -> error(get())
        }
    }

    fun <T> returnSuccessOrThrow(returnSuccess: (S) -> T, throwE: (E) -> Exception): T {
        return when(this) {
            is Success -> returnSuccess(get())
            is Error -> throw throwE(get())
        }
    }

    override fun equals(other: Any?) =
        this.toString() == other.toString() && this.hashCode() == other.hashCode()

    override fun toString() = "Result: ${success()?.get() ?: error()?.get()}"

    override fun hashCode() = Objects.hash(success()?.get(), error()?.get())
}
