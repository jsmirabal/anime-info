package com.jsmirabal.animeinfo.data

import com.jsmirabal.animeinfo.data.service.api.Anime
import com.jsmirabal.animeinfo.data.service.api.Top
import com.jsmirabal.animeinfo.data.service.model.DataGenericAnime
import com.jsmirabal.animeinfo.data.service.model.DataTopItems
import com.jsmirabal.animeinfo.domain.core.ResultWrapper
import com.jsmirabal.animeinfo.domain.core.WrappedException
import com.jsmirabal.animeinfo.domain.model.*
import io.mockk.mockk
import retrofit2.HttpException

internal val TYPE_ANIME = Top.Type.ANIME
internal val SUB_TYPE_AIRING = Top.SubType.AIRING
internal val ANIME_DETAIL = Anime.Request.DETAIL
internal const val PAGE_NUMBER = "1"
internal const val NO_PAGE = ""
internal const val DUMMY_ERROR_MESSAGE = "Some Error"
internal const val ANIME_ID = "5114"

internal val dummyDataTopItems = mockk<DataTopItems>()
internal val dummyDataTopItemsResultSuccess = mockk<ResultWrapper.Success<DataTopItems>>()

internal val dummyDataAnime = mockk<DataGenericAnime>()
internal val dummyDataAnimeResultSuccess = mockk<ResultWrapper.Success<DataGenericAnime>>()

internal val dummyDomainAnimeDetail = mockk<DomainAnimeDetail>()
internal val dummyDomainAnimeDetailResultSuccess = mockk<ResultWrapper.Success<DomainAnimeDetail>>()
internal val dummyDomainAnimeDetailResultError = mockk<ResultWrapper.Error<DomainLayerError>>()

internal val dummyDomainAnimeVideos = mockk<DomainAnimeVideos>()
internal val dummyDomainAnimeVideosResultSuccess = mockk<ResultWrapper.Success<DomainAnimeVideos>>()
internal val dummyDomainAnimeVideosResultError = mockk<ResultWrapper.Error<DomainLayerError>>()

internal val dummyDomainTopAnimes = mockk<DomainTopAnimes>()
internal val dummyDomainTopAnimesResultSuccess = mockk<ResultWrapper.Success<DomainTopAnimes>>()
internal val dummyDomainTopAnimesResultError = mockk<ResultWrapper.Error<DomainLayerError>>()

internal val dummyDomainRecommendedAnime = mockk<DomainRecommendedAnime>()
internal val dummyDomainRecommendedAnimeResultSuccess = mockk<ResultWrapper.Success<DomainRecommendedAnime>>()

internal val dummyException = Exception()
internal val dummyExceptionWithMessage = Exception(DUMMY_ERROR_MESSAGE)
internal val dummyHttpException = mockk<HttpException>()
internal val dummyBusinessError = mockk<DomainLayerError.BusinessError>()
internal val dummyWrappedException = WrappedException(dummyBusinessError)

internal val dummyTopItems = listOf(
    DataGenericAnime().apply {
        putAll(
            mapOf(
                "mal_id" to 40591,
                "rank" to 1,
                "title" to "Kaguya-sama wa Kokurasetai?: Tensai-tachi no Renai Zunousen",
                "url" to "https://myanimelist.net/anime/40591/Kaguya-sama_wa_Kokurasetai__Tensai-tachi_no_Renai_Zunousen",
                "image_url" to "https://cdn.myanimelist.net/images/anime/1764/106659.jpg?s=aab19d0f19e345e223dc26542ac3a28f",
                "type" to "TV",
                "episodes" to 12,
                "start_date" to "Apr 2020",
                "end_date" to null,
                "members" to 306836,
                "score" to 8.85
            )
        )
    }
)

internal val dummyAnime = DataGenericAnime().apply {
    putAll(
        mapOf(
            "mal_id" to 40591,
            "rank" to 1,
            "title" to "Kaguya-sama wa Kokurasetai?: Tensai-tachi no Renai Zunousen",
            "url" to "https://myanimelist.net/anime/40591/Kaguya-sama_wa_Kokurasetai__Tensai-tachi_no_Renai_Zunousen",
            "image_url" to "https://cdn.myanimelist.net/images/anime/1764/106659.jpg?s=aab19d0f19e345e223dc26542ac3a28f",
            "type" to "TV",
            "episodes" to 12,
            "start_date" to "Apr 2020",
            "end_date" to null,
            "members" to 306836,
            "score" to 8.85,
            "synopsis" to "This is a good anime."
        )
    )
}

internal val dummyAnimeVideosRaw = DataGenericAnime().apply {
    putAll(
        mapOf(
            "promo" to listOf(
                mapOf(
                    "title" to "",
                    "image_url" to "",
                    "video_url" to ""
                )
            ),
            "episodes" to listOf(
                mapOf(
                    "title" to "",
                    "episode" to "",
                    "url" to "",
                    "image_url" to ""
                )
            )
        )
    )
}

private val definition = AnimeDefinitionImpl(0, "", "")
private val extension = AnimeExtensionImpl(0, "", "", 0, "", "", 0, 0F)

internal val dummyAnimeVideosMapped = DomainAnimeVideos (
    listOf(
        DomainAnimeVideos.Episode(definition, "", "")
    ),
    listOf(
        DomainAnimeVideos.Trailer(definition, "")
    )
)

internal val dummyAnimeDetailMapped = DomainAnimeDetail(definition, extension, "")

internal val dummyAnimeCompactMapped = AnimeCompact(definition, extension)

internal val dummyTopAnimesMapped = DomainTopAnimes(
    listOf(
        dummyAnimeCompactMapped,
        dummyAnimeCompactMapped,
        dummyAnimeCompactMapped,
        dummyAnimeCompactMapped
    )
)

internal val dummyRecommendedAnimeMapped = DomainRecommendedAnime(definition, "", dummyAnimeVideosMapped.trailers)
