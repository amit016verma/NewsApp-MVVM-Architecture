package com.amitverma.newsapp.data.repository

import com.amitverma.newsapp.data.api.NetworkService
import com.amitverma.newsapp.data.local.dao.TopHeadlinesDao
import com.amitverma.newsapp.data.local.entity.Article
import com.amitverma.newsapp.data.model.topheadlines.APIArticle
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class NewsRepository @Inject constructor(
    private val networkService: NetworkService, private val topHeadlinesDao: TopHeadlinesDao
) {

    fun getTopHeadlines(country: String): Flow<List<APIArticle>> {
        return flow {
            emit(networkService.getTopHeadlines(country))
        }.map {
            it.articles
        }
    }

    fun getNewsBySources(sources: String): Flow<List<APIArticle>> {
        return flow {
            emit(networkService.getNewsBySources(sources))
        }.map {
            it.articles
        }
    }

    fun getNewsSourceArticleByDB(sourceID: String): Flow<List<Article>> {
        return topHeadlinesDao.getNewsSourceArticle(sourceID)
    }

    fun insertNewsBySources(sourceID: String, articles: List<Article>): Flow<List<Long>> {
        return flow {
            emit(topHeadlinesDao.insertAndDeleteSourceArticles(sourceID, articles))
        }
    }

    fun insertNewsByCountry(country: String, articles: List<Article>): Flow<List<Long>> {
        return flow {
            emit(topHeadlinesDao.insertAndDeleteTopHeadlineArticles(country, articles))
        }
    }

    fun getNewsByCountry(country: String): Flow<List<Article>> {
        return topHeadlinesDao.getAllTopHeadlinesArticle(country)
    }

    fun getNewsByLanguage(languageCode: String): Flow<List<APIArticle>> {
        return flow {
            emit(networkService.getNewsByLanguage(languageCode))
        }.map {
            it.articles
        }
    }

    fun insertNewsByLanguage(language: String, articles: List<Article>): Flow<List<Long>> {
        return flow {
            emit(topHeadlinesDao.insertAndDeleteLanguageArticles(language, articles))
        }
    }

    fun fetchNewsByArticleByLanguage(language: String): Flow<List<Article>> {
        return topHeadlinesDao.getNewsLanguageArticle(language)
    }
}