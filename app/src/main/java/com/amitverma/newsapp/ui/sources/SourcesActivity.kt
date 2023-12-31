package com.amitverma.newsapp.ui.sources

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.amitverma.newsapp.data.local.entity.NewsSource
import com.amitverma.newsapp.databinding.ActivitySourcesBinding
import com.amitverma.newsapp.ui.base.BaseActivity
import com.amitverma.newsapp.ui.newsListScreen.NewsListActivity
import com.amitverma.newsapp.utils.AppConstant
import com.amitverma.newsapp.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SourcesActivity : BaseActivity<SourcesViewModel, ActivitySourcesBinding>() {


    @Inject
    lateinit var sourcesAdapter: NewsSourcesListAdapter


    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, SourcesActivity::class.java)
        }
    }


    override fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.data.collect {
                    when (it.status) {
                        Status.LOADING -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                            binding.includeLayout.errorLayout.visibility = View.GONE
                        }
                        Status.ERROR -> {
                            binding.includeLayout.errorLayout.visibility = View.VISIBLE
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this@SourcesActivity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                        Status.SUCCESS -> {
                            binding.progressBar.visibility = View.GONE
                            binding.includeLayout.errorLayout.visibility = View.GONE
                            it.data?.let { sourceList ->
                                renderList(sourceList as List<NewsSource>)
                            }
                            binding.recyclerView.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    override fun setupViewModel() {
        viewModel = ViewModelProvider(this)[SourcesViewModel::class.java]
    }

    private fun renderList(newsList: List<NewsSource>) {
        sourcesAdapter.updateList(newsList)
        println("sourcesAdapter count ::" + sourcesAdapter.itemCount + " newsList size ::" + newsList.size)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        val recyclerView = binding.recyclerView
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@SourcesActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this.context, (this.layoutManager as LinearLayoutManager).orientation
                )
            )
            adapter = sourcesAdapter
        }
        binding.includeLayout.tryAgainBtn.setOnClickListener {
            viewModel.fetchNewsSources()
        }

        sourcesAdapter.itemClickListener = { _, newsSource ->
            startActivity(
                NewsListActivity.getStartIntent(
                    context = this@SourcesActivity,
                    newsSource = newsSource.sourceId,
                    newsType = AppConstant.NEWS_BY_SOURCES
                )
            )
        }
    }

    override fun setupViewBinding(inflater: LayoutInflater): ActivitySourcesBinding {
        return ActivitySourcesBinding.inflate(inflater)
    }
}