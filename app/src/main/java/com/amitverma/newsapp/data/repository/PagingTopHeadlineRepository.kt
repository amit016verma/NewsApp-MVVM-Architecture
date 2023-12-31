package com.amitverma.newsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.amitverma.newsapp.data.api.NetworkService
import com.amitverma.newsapp.data.local.dao.TopHeadlinesDao
import com.amitverma.newsapp.data.model.topheadlines.APIArticle
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class PagingTopHeadlineRepository @Inject constructor(
    private val networkService: NetworkService, private val topHeadlinesDao: TopHeadlinesDao
) {

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }

    fun getTopHeadlines(country: String): Flow<PagingData<APIArticle>> {
        return Pager(config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false
        ), pagingSourceFactory = {
            TopHeadlinePagingSource(networkService, country)
        }).flow
    }
}