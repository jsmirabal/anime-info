package com.jsmirabal.animeinfo.data.service.model

import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap

data class DataTopItems(
    @SerializedName("request_cached")
    val requestCached: Boolean,
    @SerializedName("request_cache_expiry")
    val requestCacheExpiry: String,
    @SerializedName("request_hash")
    val requestHash: String,
    @SerializedName("top")
    val topItems: List<LinkedTreeMap<Any, Any>>
)
