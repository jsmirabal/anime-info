package com.jsmirabal.animeinfo.data.service

import com.jsmirabal.animeinfo.data.service.api.Anime
import com.jsmirabal.animeinfo.data.service.api.AnimeApi
import com.jsmirabal.animeinfo.data.service.api.Season
import com.jsmirabal.animeinfo.data.service.api.Top
import com.jsmirabal.animeinfo.data.service.model.DataLayerError
import com.jsmirabal.animeinfo.domain.core.ResultWrapper
import retrofit2.HttpException
import javax.inject.Inject

class AnimeServiceImpl @Inject constructor(
    private val animeApi: AnimeApi
) : AnimeService {

    override suspend fun fetchTopItems(type: Top.Type, subType: Top.SubType, page: String) = try {
        ResultWrapper.Success(animeApi.fetchTopItems(type.get(), subType.get(), page))
    } catch (e: Exception) {
        getError(e)
    }

    override suspend fun fetchAnime(id: String, request: Anime.Request, page: String) = try {
        ResultWrapper.Success(animeApi.fetchAnime(id, request.get(), page))
    } catch (e: Exception) {
        getError(e)
    }

    override suspend fun fetchSeason(year: String, season: Season) = try {
        ResultWrapper.Success(animeApi.fetchSeason(year, season.get()))
    } catch (e: Exception) {
        getError(e)
    }

    private fun getError(e: Exception): ResultWrapper.Error<DataLayerError> {
        val error = when {
            e.message.isNullOrEmpty() -> DataLayerError.UnknownError("An unexpected error has occurred")
            e is HttpException -> DataLayerError.ServerError(e.message(), e.response())
            else -> DataLayerError.ClientError(e.message!!) // Kotlin is too dramatic sometimes
        }

        return ResultWrapper.Error(error)
    }
}
