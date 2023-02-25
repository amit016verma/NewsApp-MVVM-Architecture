package com.amitverma.newsapp.ui.country

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.amitverma.newsapp.data.local.entity.Country
import com.amitverma.newsapp.databinding.NewsSourcesItemLayoutBinding
import com.amitverma.newsapp.utils.ItemClickListener


class CountriesListAdapter(private val newsSourceList: ArrayList<Country>) :
    ListAdapter<Country, CountriesListViewHolder>(NewsSourcesListDiffCallback()) {

    lateinit var itemClickListener: ItemClickListener<Country>

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): CountriesListViewHolder = CountriesListViewHolder(
        NewsSourcesItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: CountriesListViewHolder, position: Int) {
        holder.bindView(newsSourceList[position], itemClickListener)
    }

    class NewsSourcesListDiffCallback : DiffUtil.ItemCallback<Country>() {
        override fun areItemsTheSame(
            oldItem: Country, newItem: Country
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Country, newItem: Country
        ): Boolean {
            return oldItem == newItem
        }
    }

    fun updateList(list: List<Country>) {
        /*val newList = mutableListOf<NewsSource>()
        for (item in guestList) {
            newList.add(item.copy())
        }*/
        newsSourceList.addAll(list)
        submitList(newsSourceList)
    }

}