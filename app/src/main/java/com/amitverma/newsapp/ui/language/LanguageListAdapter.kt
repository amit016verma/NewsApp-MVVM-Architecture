package com.amitverma.newsapp.ui.language

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.amitverma.newsapp.data.model.Language
import com.amitverma.newsapp.databinding.LanguageItemLayoutBinding


class LanguageListAdapter(list: ArrayList<Language>) :
    ListAdapter<Language, LanguageListViewHolder>(LanguageListDiffCallback()) {

    val newsSourceList = list
    var tracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): LanguageListViewHolder = LanguageListViewHolder(
        LanguageItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: LanguageListViewHolder, position: Int) {
        tracker?.let {
            holder.bindView(newsSourceList[position], it.isSelected(position.toLong()))
        }
    }

    class LanguageListDiffCallback : DiffUtil.ItemCallback<Language>() {
        override fun areItemsTheSame(
            oldItem: Language, newItem: Language
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Language, newItem: Language
        ): Boolean {
            return oldItem == newItem
        }
    }

    fun updateList(list: List<Language>) {
        /*val newList = mutableListOf<NewsSource>()
        for (item in guestList) {
            newList.add(item.copy())
        }*/
        newsSourceList.addAll(list)
        submitList(newsSourceList)
    }

    override fun getItemId(position: Int): Long = position.toLong()


}