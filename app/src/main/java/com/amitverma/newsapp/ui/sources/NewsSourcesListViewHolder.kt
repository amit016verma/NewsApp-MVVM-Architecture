package com.amitverma.newsapp.ui.sources

import androidx.recyclerview.widget.RecyclerView
import com.amitverma.newsapp.data.local.entity.NewsSource
import com.amitverma.newsapp.databinding.NewsSourcesItemLayoutBinding
import com.amitverma.newsapp.utils.ItemClickListener


class NewsSourcesListViewHolder(private val binding: NewsSourcesItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindView(newsSource: NewsSource, itemClickListener: ItemClickListener<NewsSource>) {
        binding.newsSourceBtn.text = newsSource.name
        itemView.setOnClickListener {
            itemClickListener(bindingAdapterPosition, newsSource)

        }
    }


}
