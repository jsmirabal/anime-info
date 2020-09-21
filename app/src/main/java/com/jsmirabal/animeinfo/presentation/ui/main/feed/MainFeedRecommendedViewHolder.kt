package com.jsmirabal.animeinfo.presentation.ui.main.feed

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jsmirabal.animeinfo.databinding.RecommendedAnimeItemViewBinding
import com.jsmirabal.animeinfo.domain.model.DomainAnimeVideos
import com.jsmirabal.animeinfo.domain.model.DomainRecommendedAnime
import com.jsmirabal.animeinfo.presentation.ui.base.BaseViewHolder

class MainFeedRecommendedViewHolder(
    override val root: View
) : RecyclerView.ViewHolder(root), BaseViewHolder<DomainRecommendedAnime> {

    private val binding = RecommendedAnimeItemViewBinding.bind(root)

    override fun bind(item: DomainRecommendedAnime) {
        binding.animeTitle.text = item.title
        binding.animeDescription.text = item.synopsis
        binding.posterImage.load(item.imageUrl)

        setActionButtons(item.trailers)
    }

    private fun setActionButtons(trailers: List<DomainAnimeVideos.Trailer>) {
        val action1 = trailers.getOrNull(0)
        val action2 = trailers.getOrNull(1)

        if (action1 != null) {
            binding.action1.text = action1.title
        }

        if (action2 != null) {
            binding.action2.text = action2.title
        }
    }
}
