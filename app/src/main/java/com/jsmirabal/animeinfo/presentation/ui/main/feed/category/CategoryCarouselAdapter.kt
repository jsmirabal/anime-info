package com.jsmirabal.animeinfo.presentation.ui.main.feed.category

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jsmirabal.animeinfo.R
import com.jsmirabal.animeinfo.domain.model.base.AnimeCompact
import com.jsmirabal.animeinfo.presentation.core.extension.inflate

class CategoryCarouselAdapter(
    private val posterItems: MutableList<AnimeCompact>
) : RecyclerView.Adapter<CategoryCarouselViewHolder>() {

    override fun getItemCount() = posterItems.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CategoryCarouselViewHolder(
        parent.inflate(R.layout.anime_poster_item_view)
    )

    override fun onBindViewHolder(holder: CategoryCarouselViewHolder, position: Int) {
        holder.bind(posterItems[position])
    }

    fun update(newItems: List<AnimeCompact>) {
        posterItems.clear()
        posterItems.addAll(newItems)
        notifyDataSetChanged()
    }

}
