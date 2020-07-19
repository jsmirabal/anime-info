package com.jsmirabal.animeinfo.domain.model

import com.jsmirabal.animeinfo.data.service.model.DataLayerError

sealed class DomainLayerError {
    class BusinessError(val cause: String) : DomainLayerError()
    class DelegateError(val delegate: DataLayerError) : DomainLayerError()
}
