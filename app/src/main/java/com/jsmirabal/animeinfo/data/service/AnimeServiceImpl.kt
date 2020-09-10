package com.jsmirabal.animeinfo.data.service

import com.jsmirabal.animeinfo.data.service.api.AnimeApi
import com.jsmirabal.animeinfo.data.service.api.AnimeApi.SubType
import com.jsmirabal.animeinfo.data.service.api.AnimeApi.Type
import com.jsmirabal.animeinfo.data.service.model.DataLayerError
import com.jsmirabal.animeinfo.domain.core.ResultWrapper
import retrofit2.HttpException

class AnimeServiceImpl(private val animeApi: AnimeApi) : AnimeService {

    override suspend fun fetchTopItems(type: Type, subType: SubType, page: String) = try {
        ResultWrapper.Success(animeApi.fetchTopItems(type.get(), subType.get(), page))
    } catch (e: Exception) {
        getError(e)
    }

    override suspend fun fetchAnimeDetail(id: String) = try {
        ResultWrapper.Success(animeApi.fetchAnimeDetail(id))
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
