package com.jsmirabal.animeinfo.presentation.ui.main.feed.category

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jsmirabal.animeinfo.databinding.AnimePosterItemViewBinding
import com.jsmirabal.animeinfo.domain.model.base.AnimeCompact
import com.jsmirabal.animeinfo.presentation.ui.base.BaseViewHolder

class CategoryCarouselViewHolder(
    override val root: View
) : RecyclerView.ViewHolder(root), BaseViewHolder<AnimeCompact> {

    private val binding = AnimePosterItemViewBinding.bind(root)

    override fun bind(item: AnimeCompact) {
        with(binding) {
            animeTitle.text = item.title
            animeEpisodesValue.text = item.episodes.toString()
            animeRatingValue.text = item.score.toString()
            posterImage.load(item.imageUrl)
        }
    }
}
