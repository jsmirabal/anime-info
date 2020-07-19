package com.jsmirabal.animeinfo.data

object TestLogger {
    fun given(message: String) = println("|-- Given: $message --|")
    fun and(message: String) = println("|-- And: $message --|")
    fun whenever(message: String) = println("|-- Whenever: $message --|")
    fun then(message: String) = println("|-- Then: $message --|")
    fun finally(message: String) = println("|-- Finally: $message --|")
    fun log(message: String) = println("|-- $message --|")
}
