package com.jsmirabal.animeinfo.data.service

import com.jsmirabal.animeinfo.data.*
import com.jsmirabal.animeinfo.data.service.api.AnimeApi
import com.jsmirabal.animeinfo.data.service.model.DataLayerError
import com.jsmirabal.animeinfo.domain.core.ResultWrapper
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
internal class AnimeServiceImplTest {

    private val animeApi: AnimeApi = mockk()
    private val service: AnimeService = AnimeServiceImpl(animeApi)

    @AfterEach
    fun afterEach() {
        clearAllMocks()
    }

    @Test
    fun `fetch top items successfully`() = runBlockingTest {
        TestLogger.given("AnimeApi returns mocked data")
        coEvery { fetchTopItems() } returns dummyDataTopItems

        TestLogger.whenever("AnimeService fetches top items")
        val result = service.fetchTopItems(TYPE_ANIME, SUB_TYPE_AIRING, PAGE_NUMBER)

        TestLogger.then("Validates AnimeService returned expected data")
        result.success()?.get() shouldEqual dummyDataTopItems

        TestLogger.then("Validates AnimeApi#fetchTopItems() was called")
        coVerify { fetchTopItems() }

        TestLogger.finally("Validates every method called from Retrofit was verified")
        confirmVerified(animeApi)
    }

    @Test
    fun `fetch anime data successfully`() = runBlockingTest {
        TestLogger.given("AnimeApi returns mocked data")
        coEvery { fetchAnime() } returns dummyDataAnime

        TestLogger.whenever("AnimeService fetches Anime Data")
        val result = service.fetchAnime(ANIME_ID, ANIME_DETAIL, PAGE_NUMBER)

        TestLogger.then("Validates AnimeService returned expected data")
        result.success()?.get() shouldEqual dummyDataAnime

        TestLogger.then("Validates AnimeApi#fetchAnime() was called")
        coVerify { fetchAnime() }

        TestLogger.finally("Validates every method called from Retrofit was verified")
        confirmVerified(animeApi)
    }

    @Test
    fun `fetch season successfully`() = runBlockingTest {
        TestLogger.given("AnimeApi returns mocked data")
        coEvery { fetchSeason() } returns dummyDataSeasonAnimes

        TestLogger.whenever("AnimeService fetches Anime Season")
        val result = service.fetchSeason(SEASON_YEAR, SEASON_WINTER)

        TestLogger.then("Validates AnimeService returned expected data")
        result.success()?.get() shouldEqual dummyDataSeasonAnimes

        TestLogger.then("Validates AnimeApi#fetchSeason() was called")
        coVerify { fetchSeason() }

        TestLogger.finally("Validates every method called from Retrofit was verified")
        confirmVerified(animeApi)
    }

    @Test
    fun `fetch top items then return ServerError`() = runBlockingTest {
        TestLogger.given("AnimeApi returns mocked data")
        coEvery { fetchTopItems() } throws dummyHttpException

        TestLogger.and("HttpException returns mocked data")
        coEvery { dummyHttpException.message() } returns DUMMY_ERROR_MESSAGE
        coEvery { dummyHttpException.message } returns DUMMY_ERROR_MESSAGE
        coEvery { dummyHttpException.response() } returns mockk<Response<Any>>()

        TestLogger.whenever("AnimeService fetches top items")
        val result = service.fetchTopItems(TYPE_ANIME, SUB_TYPE_AIRING, PAGE_NUMBER)

        TestLogger.then("Validates AnimeService returned an error")
        result shouldBeInstanceOf ResultWrapper.Error::class.java

        TestLogger.then("Validates AnimeService returned ServerError")
        result.error()?.get() shouldBeInstanceOf DataLayerError.ServerError::class.java

        TestLogger.then("Validates AnimeService returned the same error message")
        (result.error()?.get() as DataLayerError.ServerError).message shouldEqual DUMMY_ERROR_MESSAGE

        TestLogger.then("Validates AnimeApi#fetchTopItems() was called")
        coVerify { fetchTopItems() }

        TestLogger.finally("Validates every method called from AnimeApi was verified")
        confirmVerified(animeApi)
    }

    @Test
    fun `fetch top items then return ClientError`() = runBlockingTest {
        TestLogger.given("AnimeApi returns mocked data")
        coEvery { fetchTopItems() } throws dummyExceptionWithMessage

        TestLogger.whenever("AnimeService fetches top items")
        val result = service.fetchTopItems(TYPE_ANIME, SUB_TYPE_AIRING, PAGE_NUMBER)

        TestLogger.then("Validates AnimeService returned an error")
        result shouldBeInstanceOf ResultWrapper.Error::class.java

        TestLogger.then("Validates AnimeService returned ClientError")
        result.error()?.get() shouldBeInstanceOf DataLayerError.ClientError::class.java

        TestLogger.then("Validates AnimeApi#fetchTopItems() was called")
        coVerify { fetchTopItems() }

        TestLogger.finally("Validates every method called from AnimeApi was verified")
        confirmVerified(animeApi)
    }

    @Test
    fun `fetch top items then return UnknownError`() = runBlockingTest {
        TestLogger.given("AnimeApi returns mocked data")
        coEvery { fetchTopItems() } throws dummyException

        TestLogger.whenever("AnimeService fetches top items")
        val result = service.fetchTopItems(TYPE_ANIME, SUB_TYPE_AIRING, PAGE_NUMBER)

        TestLogger.then("Validates AnimeService returned an error")
        result shouldBeInstanceOf ResultWrapper.Error::class.java

        TestLogger.then("Validates AnimeService returned UnknownError")
        result.error()?.get() shouldBeInstanceOf DataLayerError.UnknownError::class.java

        TestLogger.then("Validates AnimeApi#fetchTopItems() was called")
        coVerify { fetchTopItems() }

        TestLogger.finally("Validates every method called from AnimeApi was verified")
        confirmVerified(animeApi)
    }

    private suspend fun fetchTopItems() =
        animeApi.fetchTopItems(TYPE_ANIME.get(), SUB_TYPE_AIRING.get(), PAGE_NUMBER)

    private suspend fun fetchAnime() =
        animeApi.fetchAnime(ANIME_ID, ANIME_DETAIL.get(), PAGE_NUMBER)

    private suspend fun fetchSeason() =
        animeApi.fetchSeason(SEASON_YEAR, SEASON_WINTER.get())
}
