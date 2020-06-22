package com.jsmirabal.animeinfo.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DomainTopAnimes(val topAnimes: List<AnimeInfo>) : Parcelable