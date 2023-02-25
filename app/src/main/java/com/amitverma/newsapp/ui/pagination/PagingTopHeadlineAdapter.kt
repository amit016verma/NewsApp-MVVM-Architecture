package com.amitverma.newsapp.ui.pagination

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amitverma.newsapp.data.model.topheadlines.APIArticle
import com.amitverma.newsapp.databinding.TopHeadlineItemLayoutBinding
import com.amitverma.newsapp.utils.ItemClickListener
import com.bumptech.glide.Glide

class PagingTopHeadlineAdapter :
    PagingDataAdapter<APIArticle, PagingTopHeadlineAdapter.DataViewHolder>(UIMODEL_COMPARATOR) {

    lateinit var itemClickListener: ItemClickListener<Any>

    class DataViewHolder(private val binding: TopHeadlineItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: APIArticle, itemClickListener: ItemClickListener<Any>) {
            binding.textViewTitle.text = article.title
            binding.textViewDescription.text = article.description
            binding.textViewSource.text = article.source.name

            Glide.with(binding.imageViewBanner.context).load(article.imageUrl)
                .into(binding.imageViewBanner)

            itemView.setOnClickListener {
                itemClickListener(bindingAdapterPosition, article)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        TopHeadlineItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )


    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val article = getItem(position)
        article?.let {
            holder.bind(it, itemClickListener)
        }

    }

    companion object {
        private val UIMODEL_COMPARATOR = object : DiffUtil.ItemCallback<APIArticle>() {
            override fun areItemsTheSame(oldItem: APIArticle, newItem: APIArticle): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: APIArticle, newItem: APIArticle): Boolean {
                return oldItem == newItem
            }

        }
    }

}