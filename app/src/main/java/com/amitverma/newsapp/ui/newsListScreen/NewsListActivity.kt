package com.amitverma.newsapp.ui.newsListScreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.amitverma.newsapp.data.local.entity.Article
import com.amitverma.newsapp.data.model.Language
import com.amitverma.newsapp.databinding.ActivityNewsListBinding
import com.amitverma.newsapp.ui.base.BaseActivity
import com.amitverma.newsapp.utils.AppConstant
import com.amitverma.newsapp.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NewsListActivity : BaseActivity<NewsListViewModel, ActivityNewsListBinding>() {


    @Inject
    lateinit var newsLisAdapter: NewsListAdapter

    companion object {
        private const val EXTRA_COUNTRY_ID = "EXTRA_COUNTRY_ID"
        private const val EXTRA_NEWS_TYPE = "EXTRA_NEWS_TYPE"
        private const val EXTRA_NEWS_SOURCE = "EXTRA_NEWS_SOURCE"
        private const val EXTRA_LANGUAGE_LIST = "EXTRA_LANGUAGE_LIST"

        fun getStartIntent(
            context: Context,
            countryID: String? = "",
            newsType: String,
            newsSource: String? = "",
            langList: ArrayList<Language> = ArrayList()
        ): Intent {
            return Intent(context, NewsListActivity::class.java).apply {
                putExtra(EXTRA_COUNTRY_ID, countryID)
                putExtra(EXTRA_NEWS_TYPE, newsType)
                putExtra(EXTRA_NEWS_SOURCE, newsSource)
                putParcelableArrayListExtra(EXTRA_LANGUAGE_LIST, langList)
            }
        }
    }

    private fun getIntentData() {
        intent.extras?.apply {
            val newsType = getString(EXTRA_NEWS_TYPE)
            newsType?.let { type ->
                when (type) {
                    AppConstant.NEWS_BY_SOURCES -> {
                        val source = getString(EXTRA_NEWS_SOURCE)
                        source?.let {
                            viewModel.fetchNewsBySources(it)
                        }
                    }
                    AppConstant.NEWS_BY_COUNTRY -> {
                        val countryId = getString(EXTRA_COUNTRY_ID)
                        countryId?.let {
                            viewModel.fetchNewsByCountry(it)
                        }
                    }
                    AppConstant.NEWS_BY_LANGUAGE -> {
                        val langList =
                            getParcelableArrayList(EXTRA_LANGUAGE_LIST) ?: ArrayList<Language>()
                        if (langList.isNotEmpty()) {
                            val firstLangCode = langList[0]
                            val secLangCode = langList[1]
                            viewModel.fetchNewsByLanguage(
                                firstLangCode.id ?: "", secLangCode.id ?: ""
                            )
                        }

                    }
                }


            }
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
                            binding.progressBar.visibility = View.GONE
                            binding.includeLayout.errorLayout.visibility = View.VISIBLE
                            Toast.makeText(this@NewsListActivity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                        Status.SUCCESS -> {
                            binding.progressBar.visibility = View.GONE
                            binding.includeLayout.errorLayout.visibility = View.GONE
                            it.data?.let { newsList ->
                                renderList(newsList as List<Article>)
                            }
                            binding.recyclerView.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    override fun setupViewModel() {
        viewModel = ViewModelProvider(this)[NewsListViewModel::class.java]
    }

    private fun renderList(newsList: List<Article>) {
        newsLisAdapter.addData(newsList)
        newsLisAdapter.notifyDataSetChanged()
        println("newsLisAdapter count::" + newsLisAdapter.itemCount + " newsList size ::" + newsList.size)
    }


    override fun setupView(savedInstanceState: Bundle?) {
        val recyclerView = binding.recyclerView
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context,
                    (recyclerView.layoutManager as LinearLayoutManager).orientation
                )
            )
            adapter = newsLisAdapter
        }
        binding.includeLayout.tryAgainBtn.setOnClickListener {
            getIntentData()
        }
        newsLisAdapter.itemClickListener = { _, data ->
            val article = data as Article

            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(this@NewsListActivity, Uri.parse(article.url))
        }

        getIntentData()
    }

    override fun setupViewBinding(inflater: LayoutInflater): ActivityNewsListBinding {
        return ActivityNewsListBinding.inflate(inflater)
    }
}