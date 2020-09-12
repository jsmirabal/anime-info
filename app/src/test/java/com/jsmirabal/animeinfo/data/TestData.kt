package com.jsmirabal.animeinfo.data

import com.google.gson.internal.LinkedTreeMap
import com.jsmirabal.animeinfo.data.service.api.Anime
import com.jsmirabal.animeinfo.data.service.api.Top
import com.jsmirabal.animeinfo.data.service.model.DataTopItems
import com.jsmirabal.animeinfo.domain.core.ResultWrapper
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
internal val dummyDataTopItemsResult = mockk<ResultWrapper.Success<DataTopItems>>()

internal val dummyDataAnime = mockk<LinkedTreeMap<Any, Any>>()
internal val dummyDataAnimeResult = mockk<ResultWrapper.Success<LinkedTreeMap<Any, Any>>>()

internal val dummyDomainAnimeDetail = mockk<DomainAnimeDetail>()
internal val dummyDomainAnimeDetailResult = mockk<ResultWrapper.Success<DomainAnimeDetail>>()

internal val dummyDomainAnimeVideos = mockk<DomainAnimeVideos>()
internal val dummyDomainAnimeVideosResult = mockk<ResultWrapper.Success<DomainAnimeVideos>>()

internal val dummyDomainTopAnimes = mockk<DomainTopAnimes>()
internal val dummyDomainTopAnimesResult = mockk<ResultWrapper.Success<DomainTopAnimes>>()

internal val dummyException = Exception()
internal val dummyExceptionWithMessage = Exception(DUMMY_ERROR_MESSAGE)
internal val dummyHttpException = mockk<HttpException>()
internal val dummyBusinessError = mockk<DomainLayerError.BusinessError>()

internal val dummyTopItems = listOf(
    LinkedTreeMap<Any, Any>().apply {
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

internal val dummyAnime = LinkedTreeMap<Any, Any>().apply {
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

internal val dummyAnimeVideosRaw = LinkedTreeMap<Any, Any>().apply {
    putAll(
        mapOf(
            "promo" to listOf(
                mapOf(
                    "title" to "Announcement",
                    "image_url" to "https://i.ytimg.com/vi/--IcmZkvL0Q/mqdefault.jpg",
                    "video_url" to "https://www.youtube.com/embed/--IcmZkvL0Q?enablejsapi=1&wmode=opaque&autoplay=1"
                )
            ),
            "episodes" to listOf(
                mapOf(
                    "title" to "Journey’s End",
                    "episode" to "Episode 64",
                    "url" to "https://myanimelist.net/anime/5114/Fullmetal_Alchemist__Brotherhood/episode/64",
                    "image_url" to "https://cdn.myanimelist.net/images/icon-banned-youtube.png"
                )
            )
        )
    )
}

internal val dummyAnimeVideosMapped = DomainAnimeVideos (
    listOf(
        DomainAnimeVideos.Episode(
            AnimeDefinitionImpl(0, "Journey’s End", "https://cdn.myanimelist.net/images/icon-banned-youtube.png"),
            "https://myanimelist.net/anime/5114/Fullmetal_Alchemist__Brotherhood/episode/64",
            "Episode 64"
        )
    ),
    listOf(
        DomainAnimeVideos.Trailer(
            AnimeDefinitionImpl(0, "Announcement", "https://i.ytimg.com/vi/--IcmZkvL0Q/mqdefault.jpg"),
            "https://www.youtube.com/embed/--IcmZkvL0Q?enablejsapi=1&wmode=opaque&autoplay=1"
        )
    )
)
