package com.jsmirabal.animeinfo.data.service.model

import retrofit2.Response


sealed class DataLayerError {
    abstract val message: String

    class ServerError(override val message: String, val httpResponse: Response<*>?) : DataLayerError()
    class ClientError(override val message: String) : DataLayerError()
    class UnknownError(override val message: String) : DataLayerError()
}
