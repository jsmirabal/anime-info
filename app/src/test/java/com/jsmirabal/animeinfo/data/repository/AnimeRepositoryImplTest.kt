package com.jsmirabal.animeinfo.data.repository

import com.jsmirabal.animeinfo.data.*
import com.jsmirabal.animeinfo.data.service.AnimeService
import com.jsmirabal.animeinfo.data.service.api.AnimeApi
import com.jsmirabal.animeinfo.domain.core.ResultWrapper
import com.jsmirabal.animeinfo.domain.mapper.AnimeMapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class AnimeRepositoryImplTest {

    private val animeService = mockk<AnimeService>()
    private val domainMapper = mockk<AnimeMapper>()
    private val animeRepository = AnimeRepositoryImpl(animeService, domainMapper)

    @Test
    fun `fetch top airing animes successfully`() = runBlockingTest {

        TestLogger.given("AnimeService returns mocked data")
        coEvery { fetchTopItems() } returns dummyAnimeServiceResult

        TestLogger.and("DomainMapper returns mocked data")
        coEvery { domainMapper.mapToTopAnimes(dummyDataTopItems) } returns dummyDomainTopAnimes

        TestLogger.and("ResultWrapper.Success<DataTopItems>#get() returns mocked data")
        coEvery { dummyAnimeServiceResult.get() } returns dummyDataTopItems

        TestLogger.whenever("AnimeRepository fetches top airing animes")
        val result = animeRepository.fetchTopAiringAnimes(PAGE_NUMBER)

        TestLogger.then("Validates AnimeRepository returned expected data")
        result shouldEqual ResultWrapper.Success(dummyDomainTopAnimes)

        TestLogger.then("Validates AnimeService#fetchTopItems() was called")
        coVerify { fetchTopItems() }

        TestLogger.finally("Validates every method called from AnimeService was verified")
        confirmVerified(animeService)
    }

    @Test
    fun `fetch top airing animes then get an exception`() = runBlockingTest {
        TestLogger.given("AnimeService returns mocked data")
        coEvery { fetchTopItems() } throws dummyException

        try {
            TestLogger.whenever("AnimeRepository fetches top airing animes")
            animeRepository.fetchTopAiringAnimes(PAGE_NUMBER)
        } catch (e: Exception) {
            TestLogger.then("Validates an Exception is thrown")
            e shouldEqual dummyException
        }

        TestLogger.then("Validates AnimeService#fetchTopItems() was called")
        coVerify { fetchTopItems() }

        TestLogger.finally("Validates every method called from AnimeService was verified")
        confirmVerified(animeService)
    }

    private suspend fun fetchTopItems() =
        animeService.fetchTopItems(
            type = AnimeApi.Type.ANIME,
            subType = AnimeApi.SubType.AIRING,
            page = PAGE_NUMBER
        )
}
