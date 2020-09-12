package com.jsmirabal.animeinfo.data.service.model

import com.google.gson.annotations.SerializedName

data class DataTopItems(
    @SerializedName("top")
    val topItems: List<DataGenericAnime>
)
