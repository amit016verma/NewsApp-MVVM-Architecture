package com.amitverma.newsapp.data.repository

import com.amitverma.newsapp.data.api.NetworkService
import com.amitverma.newsapp.data.local.dao.NewsSourceDao
import com.amitverma.newsapp.data.local.entity.NewsSource
import com.amitverma.newsapp.data.model.newssources.APINewsSource
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class NewsSourceRepository @Inject constructor(
    private val networkService: NetworkService, private val newsSourceDao: NewsSourceDao
) {

    fun getNewsSources(): Flow<List<APINewsSource>> {
        return flow {
            emit(networkService.getNewsSources())
        }.map {
            it.newsSource
        }
    }

    fun saveNewsSources(newsSources: List<NewsSource>): Flow<List<Long>> {
        println("NewsSourceDao:::$newsSourceDao")
        return flow {
            emit(newsSourceDao.insertAndDeleteNewsSources(newsSources))
        }
    }

    fun getAllNewsSources(): Flow<List<NewsSource>> {
        return newsSourceDao.getAllNewsSources()
    }
}