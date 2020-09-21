package com.jsmirabal.animeinfo.presentation.ui.main.feed

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.jsmirabal.animeinfo.R
import com.jsmirabal.animeinfo.domain.model.DomainRecommendedAnime
import com.jsmirabal.animeinfo.domain.model.DomainSeasonAnimes
import com.jsmirabal.animeinfo.domain.model.DomainTopAnimes
import com.jsmirabal.animeinfo.domain.model.mainfeed.MainFeedItem
import com.jsmirabal.animeinfo.domain.model.mainfeed.MainFeedItemType
import com.jsmirabal.animeinfo.presentation.core.extension.inflate

class MainFeedAdapter(
    private val feedItems: MutableList<MainFeedItem>
) : Adapter<ViewHolder>() {

    override fun getItemCount() =
        feedItems.size

    override fun getItemViewType(position: Int) =
        feedItems[position].feedItemType.get()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        if (viewType == MainFeedItemType.RECOMMENDED.get()) {
            MainFeedRecommendedViewHolder(parent.inflate(R.layout.recommended_anime_item_view))
        } else {
            MainFeedCategoryViewHolder(parent.inflate(R.layout.category_carousel_item_view))
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(feedItems[position]) {
            when (feedItemType) {
                MainFeedItemType.RECOMMENDED -> {
                    (holder as MainFeedRecommendedViewHolder).bind(this as DomainRecommendedAnime)
                }
                MainFeedItemType.CURRENT_SEASON -> {
                    (holder as MainFeedCategoryViewHolder).bind(this as DomainSeasonAnimes)
                }
                else -> {
                    (holder as MainFeedCategoryViewHolder).bind(this as DomainTopAnimes)
                }
            }
        }
    }

    fun update(newItems: List<MainFeedItem>) {
        feedItems.clear()
        feedItems.addAll(newItems)
        notifyDataSetChanged()
    }
}
