package com.jsmirabal.animeinfo.data

import com.google.gson.annotations.SerializedName

data class AnimeServiceModel(
    @SerializedName("request_cached")
    val requestCached: Boolean,
    @SerializedName("request_cache_expiry")
    val requestCacheExpiry: String,
    @SerializedName("request_hash")
    val requestHash: String,
    @SerializedName("top")
    val topItems: List<Any>
)
