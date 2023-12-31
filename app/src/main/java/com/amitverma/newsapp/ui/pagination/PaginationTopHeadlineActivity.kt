package com.amitverma.newsapp.ui.pagination

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.amitverma.newsapp.data.model.topheadlines.APIArticle
import com.amitverma.newsapp.databinding.ActivityPaginationTopHeadlineBinding
import com.amitverma.newsapp.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PaginationTopHeadlineActivity :
    BaseActivity<PagingTopHeadlineViewModel, ActivityPaginationTopHeadlineBinding>() {

    @Inject
    lateinit var topHeadlineAdapter: PagingTopHeadlineAdapter

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, PaginationTopHeadlineActivity::class.java)
        }
    }

    override fun setupObserver() {
        super.setupObserver()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pagingDataFlow.collectLatest { pagingData ->
                    binding.progressBar.visibility = View.GONE
                    binding.includeLayout.errorLayout.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    topHeadlineAdapter.submitData(pagingData)
                }
            }
        }

    }

    override fun setupViewModel() {
        viewModel = ViewModelProvider(this)[PagingTopHeadlineViewModel::class.java]
    }

    override fun setupView(savedInstanceState: Bundle?) {
        val recyclerView = binding.recyclerView
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@PaginationTopHeadlineActivity)
            addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context,
                    (recyclerView.layoutManager as LinearLayoutManager).orientation
                )
            )
            adapter = topHeadlineAdapter
        }

        binding.includeLayout.tryAgainBtn.setOnClickListener {
//            viewModel.startFetchingNews()
        }
        topHeadlineAdapter.itemClickListener = { _, data ->
            val article = data as APIArticle
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(this@PaginationTopHeadlineActivity, Uri.parse(article.url))
        }
    }

    override fun setupViewBinding(inflater: LayoutInflater): ActivityPaginationTopHeadlineBinding {
        return ActivityPaginationTopHeadlineBinding.inflate(inflater)
    }


}