package com.jsmirabal.animeinfo.domain.mapper

import com.google.gson.Gson
import com.jsmirabal.animeinfo.data.TestLogger
import com.jsmirabal.animeinfo.data.dummyDataTopItems
import com.jsmirabal.animeinfo.data.dummyTopItems
import com.jsmirabal.animeinfo.domain.model.DomainTopAnimes
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class AnimeMapperTest {

    private val mapper = AnimeMapper(Gson())

    @Test
    fun `map DataTopItems to DomainTopAnimes`() = runBlockingTest {
        TestLogger.given("DataTopItems.topItems returns mocked data")
        every { dummyDataTopItems.topItems } returns dummyTopItems

        TestLogger.whenever("AnimeMapper maps DataTopItems to DomainTopAnimes")
        val result = mapper.mapToTopAnimes(dummyDataTopItems)

        TestLogger.then("Validates expected instance returned")
        result shouldBeInstanceOf DomainTopAnimes::class.java

        TestLogger.then("Validates input and output items data is the same")
        result.topAnimes.first().id shouldEqual dummyTopItems.first()["mal_id"]

        TestLogger.then("Validates DataTopItems#topItems was called")
        coVerify { dummyDataTopItems.topItems }

        TestLogger.finally("Validates every method called from DataTopItems was verified")
        confirmVerified(dummyDataTopItems)
    }
}