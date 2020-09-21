package com.jsmirabal.animeinfo.presentation.ui.main.feed

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jsmirabal.animeinfo.databinding.CategoryCarouselItemViewBinding
import com.jsmirabal.animeinfo.domain.model.DomainSeasonAnimes
import com.jsmirabal.animeinfo.domain.model.DomainTopAnimes
import com.jsmirabal.animeinfo.domain.model.mainfeed.MainFeedItem
import com.jsmirabal.animeinfo.domain.model.mainfeed.MainFeedItemType
import com.jsmirabal.animeinfo.presentation.ui.base.BaseViewHolder
import com.jsmirabal.animeinfo.presentation.ui.main.feed.category.CategoryCarouselAdapter

class MainFeedCategoryViewHolder(
    override val root: View
) : RecyclerView.ViewHolder(root), BaseViewHolder<MainFeedItem> {

    private val binding = CategoryCarouselItemViewBinding.bind(root)
    private val categoryCarouselAdapter = CategoryCarouselAdapter(mutableListOf())

    init {
        with(binding.categoryCarousel) {
            layoutManager = LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)
            if (adapter == null) adapter = categoryCarouselAdapter
        }
    }

    override fun bind(item: MainFeedItem) {
        setCategoryText(item)
        updateCarousel(item)
    }

    private fun updateCarousel(item: MainFeedItem) {
        categoryCarouselAdapter.update(
            if (item is DomainSeasonAnimes) {
                item.animes
            } else {
                (item as DomainTopAnimes).topAnimes
            }
        )
    }

    private fun setCategoryText(item: MainFeedItem) {
        binding.categoryTitle.text = when (item.feedItemType) {
            MainFeedItemType.CURRENT_SEASON -> (item as DomainSeasonAnimes).run {
                "$seasonName $seasonYear Animes"
            }
            // I know but... I don't own the API
            MainFeedItemType.TOP_AIRING_ANIMES -> "Top Airing Animes"

            MainFeedItemType.TOP_UPCOMING_ANIMES -> "Top Upcoming Animes"

            MainFeedItemType.MOST_POPULAR_ANIMES -> "Most Popular Animes"

            MainFeedItemType.MOST_FAVORITE_ANIMES -> "Most Favorite Animes"

            else -> "Unknown Category (sore wa nani?)"
        }
    }
}
