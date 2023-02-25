package com.amitverma.newsapp.data.repository

import com.amitverma.newsapp.data.api.NetworkService
import com.amitverma.newsapp.data.local.dao.TopHeadlinesDao
import com.amitverma.newsapp.data.local.entity.Article
import com.amitverma.newsapp.data.model.topheadlines.APIArticle
import com.amitverma.newsapp.di.ActivityScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ActivityScope
class TopHeadlineRepository @Inject constructor(
    private val networkService: NetworkService, private val topHeadlinesDao: TopHeadlinesDao
) {

    fun getTopHeadlines(country: String): Flow<List<APIArticle>> {
        return flow {
            emit(networkService.getTopHeadlines(country))
        }.map {
            it.articles
        }
    }

    fun saveTopHeadlinesArticles(articles: List<Article>, country: String): Flow<List<Long>> {
        return flow {
            emit(topHeadlinesDao.insertAndDeleteTopHeadlineArticles(country, articles))
        }
    }


    fun getAllTopHeadlinesArticles(country: String): Flow<List<Article>> {
        return topHeadlinesDao.getAllTopHeadlinesArticle(country)
    }
}