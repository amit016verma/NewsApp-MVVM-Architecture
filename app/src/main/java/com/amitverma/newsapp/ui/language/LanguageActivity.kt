package com.amitverma.newsapp.ui.language

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.selection.Selection
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.amitverma.newsapp.data.model.Language
import com.amitverma.newsapp.databinding.ActivityLanguageBinding
import com.amitverma.newsapp.ui.base.BaseActivity
import com.amitverma.newsapp.ui.newsListScreen.NewsListActivity
import com.amitverma.newsapp.utils.AppConstant
import com.amitverma.newsapp.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LanguageActivity : BaseActivity<LanguageListViewModel, ActivityLanguageBinding>() {

    @Inject
    lateinit var languageListAdapter: LanguageListAdapter

    private lateinit var tracker: SelectionTracker<Long>

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, LanguageActivity::class.java)
        }
    }

    private fun setupTracker() {
        tracker = SelectionTracker.Builder(
            "mySelection",
            binding.recyclerView,
            StableIdKeyProvider(binding.recyclerView),
            MyItemDetailsLookup(binding.recyclerView),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(object : SelectionTracker.SelectionPredicate<Long>() {
            override fun canSetStateForKey(key: Long, nextState: Boolean): Boolean {
                Log.v("tracker::", "canSetStateForKey::$key")
                if (nextState && tracker.selection.size() >= 2) { // 2 - max selection size
                    Toast.makeText(
                        this@LanguageActivity,
                        "You can not select more than 2 language.",
                        Toast.LENGTH_LONG
                    ).show()
                    return false // Can't select when 2 items selected
                }
                return true
            }

            override fun canSetStateAtPosition(position: Int, nextState: Boolean): Boolean {
                return true
            }

            override fun canSelectMultiple(): Boolean {
                return true
            }

        }).build()

        tracker.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
            override fun onSelectionChanged() {
                super.onSelectionChanged()
                val nItems: Int = tracker.selection.size()
                if (nItems == 2) {
                    fetchNewsForLanguages(tracker.selection)
                }
            }
        })

        languageListAdapter.tracker = tracker
    }

    private fun fetchNewsForLanguages(selection: Selection<Long>) {
        val list = selection.map {
            languageListAdapter.newsSourceList[it.toInt()]
        }.toList()

        if (list.isNotEmpty()) {
            startActivity(
                NewsListActivity.getStartIntent(
                    this@LanguageActivity,
                    newsType = AppConstant.NEWS_BY_LANGUAGE,
                    langList = list as ArrayList<Language>
                )
            )
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
                            binding.recyclerView.visibility = View.GONE
                            binding.includeLayout.errorLayout.visibility = View.VISIBLE
                        }
                        Status.SUCCESS -> {
                            binding.progressBar.visibility = View.GONE
                            binding.recyclerView.visibility = View.VISIBLE
                            binding.includeLayout.errorLayout.visibility = View.GONE
                            it.data?.let { langList ->
                                renderList(langList as List<Language>)
                            }
                        }

                    }

                }
            }
        }
    }

    override fun setupViewModel() {
        viewModel = ViewModelProvider(this)[LanguageListViewModel::class.java]
    }

    private fun renderList(languageList: List<Language>) {
        languageListAdapter.updateList(languageList)
    }


    override fun setupView(savedInstanceState: Bundle?) {
        val recyclerView = binding.recyclerView
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@LanguageActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this.context, (this.layoutManager as LinearLayoutManager).orientation
                )
            )
            adapter = languageListAdapter
        }
        setupTracker()
    }

    override fun setupViewBinding(inflater: LayoutInflater): ActivityLanguageBinding {
        return ActivityLanguageBinding.inflate(inflater)
    }
}