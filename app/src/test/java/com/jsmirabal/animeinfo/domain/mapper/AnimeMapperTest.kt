package com.jsmirabal.animeinfo.domain.mapper

import com.google.gson.Gson
import com.jsmirabal.animeinfo.data.TestLogger
import com.jsmirabal.animeinfo.data.dummyAnime
import com.jsmirabal.animeinfo.data.dummyAnimeVideosMapped
import com.jsmirabal.animeinfo.data.dummyAnimeVideosRaw
import com.jsmirabal.animeinfo.data.dummyDataTopItems
import com.jsmirabal.animeinfo.data.dummySeasonAnimesMapped
import com.jsmirabal.animeinfo.data.dummySeasonAnimesRaw
import com.jsmirabal.animeinfo.data.dummyTopItems
import com.jsmirabal.animeinfo.domain.model.DomainAnimeDetail
import com.jsmirabal.animeinfo.domain.model.DomainAnimeVideos
import com.jsmirabal.animeinfo.domain.model.DomainSeasonAnimes
import com.jsmirabal.animeinfo.domain.model.DomainTopAnimes
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class AnimeMapperTest {

    private val mapper = AnimeMapper(Gson())

    @AfterEach
    fun afterEach() {
        clearAllMocks()
    }

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

    @Test
    fun `map DataGenericAnime to DomainAnimeDetail`() = runBlockingTest {
        TestLogger.whenever("AnimeMapper maps DataGenericAnime to DomainAnimeDetail")
        val result = mapper.mapToAnimeDetail(dummyAnime)

        TestLogger.then("Validates expected instance returned")
        result shouldBeInstanceOf DomainAnimeDetail::class.java

        TestLogger.finally("Validates input and output items data is the same")
        result.id shouldEqual dummyAnime["mal_id"]
        result.synopsis shouldEqual dummyAnime["synopsis"]
    }

    @Test
    fun `map DataGenericAnime to DomainAnimeVideos`() = runBlockingTest {
        TestLogger.whenever("AnimeMapper maps DataGenericAnime to DomainAnimeVideos")
        val result = mapper.mapToAnimeVideos(dummyAnimeVideosRaw)

        TestLogger.then("Validates expected instance returned")
        result shouldBeInstanceOf DomainAnimeVideos::class.java

        TestLogger.finally("Validates input and output items data is the same")
        result shouldEqual dummyAnimeVideosMapped
    }

    @Test
    fun `map DataSeasonAnimes to DomainSeasonAnimes`() = runBlockingTest {
        TestLogger.whenever("AnimeMapper maps DataSeasonAnimes to DomainSeasonAnimes")
        val result = mapper.mapToDomainSeasonAnimes(dummySeasonAnimesRaw)

        TestLogger.then("Validates expected instance returned")
        result shouldBeInstanceOf DomainSeasonAnimes::class.java

        TestLogger.finally("Validates input and output items data is the same")
        result shouldEqual dummySeasonAnimesMapped
    }
}
