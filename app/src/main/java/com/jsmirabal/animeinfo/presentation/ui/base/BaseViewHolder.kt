package com.jsmirabal.animeinfo.presentation.ui.base

import android.view.View

interface BaseViewHolder <T> {
    val root: View

    fun bind(item: T)
}
