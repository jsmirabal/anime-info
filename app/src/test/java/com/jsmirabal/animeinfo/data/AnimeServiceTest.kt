package com.jsmirabal.animeinfo.data

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import retrofit2.Retrofit

internal class AnimeServiceTest {

    private val retrofit: Retrofit = mockk()
    private val service: AnimeService = AnimeServiceImpl(retrofit)
    private val type = "anime"
    private val subType = "airing"
    private val page = "1"
    private val dummyModel: AnimeServiceModel = mockk()
    private val dummyError: RuntimeException = mockk()

    @Test
    fun `fetch top items successfully`() = runBlocking {
        coEvery {
            retrofit.create(AnimeApi::class.java).fetchTopItems(type, subType, page)
        } returns dummyModel

        assertDoesNotThrow("Should not throw an exception.") {
            runBlocking {
                val result = service.fetchTopItems(type, subType, page)
                result shouldEqual dummyModel
            }
        }
    }

    @Test
    fun `fetch top items then return an error`() = runBlocking {
        coEvery {
            retrofit.create(AnimeApi::class.java).fetchTopItems(type, subType, page)
        } throws dummyError

        assertThrows<RuntimeException> {
            runBlocking {
                service.fetchTopItems(type, subType, page)
            }
        }
        Unit
    }
}
