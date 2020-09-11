package com.jsmirabal.animeinfo.data

import com.google.gson.internal.LinkedTreeMap
import com.jsmirabal.animeinfo.data.service.api.Top
import com.jsmirabal.animeinfo.data.service.model.DataTopItems
import com.jsmirabal.animeinfo.domain.core.ResultWrapper
import com.jsmirabal.animeinfo.domain.model.AnimeDetail
import com.jsmirabal.animeinfo.domain.model.DomainLayerError
import com.jsmirabal.animeinfo.domain.model.DomainTopAnimes
import io.mockk.mockk
import retrofit2.HttpException

internal val TYPE_ANIME = Top.Type.ANIME
internal val SUB_TYPE_AIRING = Top.SubType.AIRING
internal const val PAGE_NUMBER = "1"
internal const val DUMMY_ERROR_MESSAGE = "Some Error"
internal const val ANIME_ID = "5114"

internal val dummyDataTopItems = mockk<DataTopItems>()
internal val dummyAnimeDetail = mockk<AnimeDetail>()
internal val dummyAnimeDetailResult = mockk<ResultWrapper.Success<AnimeDetail>>()
internal val dummyDomainTopAnimes = mockk<DomainTopAnimes>()
internal val dummyAnimeServiceResult = mockk<ResultWrapper.Success<DataTopItems>>()
internal val dummyException = Exception()
internal val dummyExceptionWithMessage = Exception(DUMMY_ERROR_MESSAGE)
internal val dummyHttpException = mockk<HttpException>()
internal val dummyBusinessError = mockk<DomainLayerError.BusinessError>()
internal val dummyTopAiringAnimes = mockk<ResultWrapper<DomainTopAnimes, DomainLayerError>>()
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
