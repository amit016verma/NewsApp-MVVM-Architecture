package com.amitverma.newsapp.ui.country

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.amitverma.newsapp.data.local.entity.Country
import com.amitverma.newsapp.databinding.ActivityCountryListBinding
import com.amitverma.newsapp.di.component.ActivityComponent
import com.amitverma.newsapp.ui.base.BaseActivity
import com.amitverma.newsapp.ui.newsListScreen.NewsListActivity
import com.amitverma.newsapp.utils.AppConstant
import com.amitverma.newsapp.utils.Status
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountryListActivity : BaseActivity<CountryListViewModel, ActivityCountryListBinding>() {


    @Inject
    lateinit var countriesListAdapter: CountriesListAdapter


    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, CountryListActivity::class.java)
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
                            it.data?.let { countryList ->
                                renderList(countryList as List<Country>)
                            }
                        }
                    }

                }
            }
        }
    }

    private fun renderList(countryList: List<Country>) {
        countriesListAdapter.updateList(countryList)
    }

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        val recyclerView = binding.recyclerView
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@CountryListActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this.context, (this.layoutManager as LinearLayoutManager).orientation
                )
            )
            adapter = countriesListAdapter
        }
        countriesListAdapter.itemClickListener = { _, country ->
            startActivity(
                NewsListActivity.getStartIntent(
                    context = this@CountryListActivity,
                    countryID = country.id,
                    newsType = AppConstant.NEWS_BY_COUNTRY
                )
            )
        }
    }

    override fun setupViewBinding(inflater: LayoutInflater): ActivityCountryListBinding {
        return ActivityCountryListBinding.inflate(inflater)
    }
}