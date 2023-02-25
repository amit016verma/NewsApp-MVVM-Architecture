package com.amitverma.newsapp.ui.sources

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.amitverma.newsapp.data.local.entity.NewsSource
import com.amitverma.newsapp.databinding.NewsSourcesItemLayoutBinding
import com.amitverma.newsapp.utils.ItemClickListener


class NewsSourcesListAdapter(private val newsSourceList: ArrayList<NewsSource>) :
    ListAdapter<NewsSource, NewsSourcesListViewHolder>(NewsSourcesListDiffCallback()) {

    lateinit var itemClickListener: ItemClickListener<NewsSource>

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): NewsSourcesListViewHolder = NewsSourcesListViewHolder(
        NewsSourcesItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: NewsSourcesListViewHolder, position: Int) {
        holder.bindView(newsSourceList[position], itemClickListener)
    }

    class NewsSourcesListDiffCallback : DiffUtil.ItemCallback<NewsSource>() {
        override fun areItemsTheSame(
            oldItem: NewsSource, newItem: NewsSource
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: NewsSource, newItem: NewsSource
        ): Boolean {
            return oldItem == newItem
        }
    }

    fun updateList(list: List<NewsSource>) {
        /*val newList = mutableListOf<NewsSource>()
        for (item in guestList) {
            newList.add(item.copy())
        }*/
        newsSourceList.addAll(list)
        submitList(newsSourceList)
    }

}