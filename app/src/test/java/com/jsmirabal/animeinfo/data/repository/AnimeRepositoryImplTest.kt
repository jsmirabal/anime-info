package com.jsmirabal.animeinfo.data.repository

import com.jsmirabal.animeinfo.data.*
import com.jsmirabal.animeinfo.data.service.AnimeService
import com.jsmirabal.animeinfo.data.service.api.Anime
import com.jsmirabal.animeinfo.data.service.api.Top
import com.jsmirabal.animeinfo.domain.core.ResultWrapper
import com.jsmirabal.animeinfo.domain.mapper.AnimeMapper
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class AnimeRepositoryImplTest {

    private val animeService = mockk<AnimeService>()
    private val domainMapper = mockk<AnimeMapper>()
    private val animeRepository = AnimeRepositoryImpl(animeService, domainMapper)

    @AfterEach
    fun afterEach() {
        clearAllMocks()
    }

    @Test
    fun `fetch top airing animes successfully`() = runBlockingTest {

        TestLogger.given("AnimeService returns mocked data")
        coEvery { fetchTopAiring() } returns dummyDataTopItemsResultSuccess

        TestLogger.and("DomainMapper returns mocked data")
        coEvery { domainMapper.mapToTopAnimes(dummyDataTopItems) } returns dummyDomainTopAnimes

        TestLogger.and("ResultWrapper.Success<DataTopItems>#get() returns mocked data")
        coEvery { dummyDataTopItemsResultSuccess.get() } returns dummyDataTopItems

        TestLogger.whenever("AnimeRepository fetches top airing animes")
        val result = animeRepository.fetchTopAiringAnimes(PAGE_NUMBER)

        TestLogger.then("Validates AnimeRepository returned expected data")
        result shouldEqual ResultWrapper.Success(dummyDomainTopAnimes)

        TestLogger.then("Validates AnimeService#fetchTopItems() was called")
        coVerify { fetchTopAiring() }

        TestLogger.finally("Validates every method called from AnimeService was verified")
        confirmVerified(animeService)
    }

    @Test
    fun `fetch top airing animes then get an exception`() = runBlockingTest {
        TestLogger.given("AnimeService returns mocked data")
        coEvery { fetchTopAiring() } throws dummyException

        try {
            TestLogger.whenever("AnimeRepository fetches top airing animes")
            animeRepository.fetchTopAiringAnimes(PAGE_NUMBER)
        } catch (e: Exception) {
            TestLogger.then("Validates an Exception is thrown")
            e shouldEqual dummyException
        }

        TestLogger.then("Validates AnimeService#fetchTopItems() was called")
        coVerify { fetchTopAiring() }

        TestLogger.finally("Validates every method called from AnimeService was verified")
        confirmVerified(animeService)
    }

    @Test
    fun `fetch top upcoming animes successfully`() = runBlockingTest {

        TestLogger.given("AnimeService returns mocked data")
        coEvery { fetchTopUpcoming() } returns dummyDataTopItemsResultSuccess

        TestLogger.and("DomainMapper returns mocked data")
        coEvery { domainMapper.mapToTopAnimes(dummyDataTopItems) } returns dummyDomainTopAnimes

        TestLogger.and("ResultWrapper.Success<DataTopItems>#get() returns mocked data")
        coEvery { dummyDataTopItemsResultSuccess.get() } returns dummyDataTopItems

        TestLogger.whenever("AnimeRepository fetches top airing animes")
        val result = animeRepository.fetchTopUpcomingAnimes(PAGE_NUMBER)

        TestLogger.then("Validates AnimeRepository returned expected data")
        result shouldEqual ResultWrapper.Success(dummyDomainTopAnimes)

        TestLogger.then("Validates AnimeService#fetchTopItems() was called")
        coVerify { fetchTopUpcoming() }

        TestLogger.finally("Validates every method called from AnimeService was verified")
        confirmVerified(animeService)
    }

    @Test
    fun `fetch anime detail successfully`() = runBlockingTest {

        TestLogger.given("AnimeService returns mocked data")
        coEvery { fetchAnimeDetail() } returns dummyDataAnimeResultSuccess

        TestLogger.and("DomainMapper returns mocked data")
        coEvery { domainMapper.mapToAnimeDetail(dummyDataAnime) } returns dummyDomainAnimeDetail

        TestLogger.and("ResultWrapper.Success<DataGenericAnime>#get() returns mocked data")
        coEvery { dummyDataAnimeResultSuccess.get() } returns dummyDataAnime

        TestLogger.whenever("AnimeRepository fetches anime detail")
        val result = animeRepository.fetchAnimeDetail(ANIME_ID)

        TestLogger.then("Validates AnimeRepository returned expected data")
        result shouldEqual ResultWrapper.Success(dummyDomainAnimeDetail)

        TestLogger.then("Validates AnimeService#fetchAnime() was called")
        coVerify { fetchAnimeDetail() }

        TestLogger.finally("Validates every method called from AnimeService was verified")
        confirmVerified(animeService)
    }

    @Test
    fun `fetch anime videos successfully`() = runBlockingTest {

        TestLogger.given("AnimeService returns mocked data")
        coEvery { fetchAnimeVideos() } returns dummyDataAnimeResultSuccess

        TestLogger.and("DomainMapper returns mocked data")
        coEvery { domainMapper.mapToAnimeVideos(dummyDataAnime) } returns dummyDomainAnimeVideos

        TestLogger.and("ResultWrapper.Success<DataGenericAnime>#get() returns mocked data")
        coEvery { dummyDataAnimeResultSuccess.get() } returns dummyDataAnime

        TestLogger.whenever("AnimeRepository fetches anime videos")
        val result = animeRepository.fetchAnimeVideos(ANIME_ID)

        TestLogger.then("Validates AnimeRepository returned expected data")
        result shouldEqual ResultWrapper.Success(dummyDomainAnimeVideos)

        TestLogger.then("Validates AnimeService#fetchAnimeVideo() was called")
        coVerify { fetchAnimeVideos() }

        TestLogger.finally("Validates every method called from AnimeService was verified")
        confirmVerified(animeService)
    }

    @Test
    fun `fetch current season successfully`() = runBlockingTest {

        TestLogger.given("AnimeService returns mocked data")
        coEvery { fetchCurrentSeason() } returns dummyDataSeasonAnimesSuccess

        TestLogger.and("DomainMapper returns mocked data")
        coEvery { domainMapper.mapToDomainSeasonAnimes(dummyDataSeasonAnimes) } returns dummyDomainSeasonAnimes

        TestLogger.and("ResultWrapper.Success<DataSeasonAnimes>#get() returns mocked data")
        coEvery { dummyDataSeasonAnimesSuccess.get() } returns dummyDataSeasonAnimes

        TestLogger.whenever("AnimeRepository fetches current season animes")
        val result = animeRepository.fetchCurrentSeason()

        TestLogger.then("Validates AnimeRepository returned expected data")
        result shouldEqual ResultWrapper.Success(dummyDomainSeasonAnimes)

        TestLogger.then("Validates AnimeService#fetchSeason() was called")
        coVerify { fetchCurrentSeason() }

        TestLogger.finally("Validates every method called from AnimeService was verified")
        confirmVerified(animeService)
    }

    private suspend fun fetchTopAiring() = animeService.fetchTopItems(
        type = Top.Type.ANIME,
        subType = Top.SubType.AIRING,
        page = PAGE_NUMBER
    )

    private suspend fun fetchTopUpcoming() = animeService.fetchTopItems(
        type = Top.Type.ANIME,
        subType = Top.SubType.UPCOMING,
        page = PAGE_NUMBER
    )

    private suspend fun fetchAnimeDetail() = animeService.fetchAnime(
        id = ANIME_ID,
        request = Anime.Request.DETAIL,
        page = NO_PAGE
    )

    private suspend fun fetchAnimeVideos() = animeService.fetchAnime(
        id = ANIME_ID,
        request = Anime.Request.VIDEOS,
        page = NO_PAGE
    )

    private suspend fun fetchCurrentSeason() = animeService.fetchSeason()
}
