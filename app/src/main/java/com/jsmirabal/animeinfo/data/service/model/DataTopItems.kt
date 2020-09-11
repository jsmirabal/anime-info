package com.jsmirabal.animeinfo.data.service.model

import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap

data class DataTopItems(
    @SerializedName("top")
    val topItems: List<LinkedTreeMap<Any, Any>>
)
