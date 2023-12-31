package com.amitverma.newsapp.data.repository

import com.amitverma.newsapp.data.api.NetworkService
import com.amitverma.newsapp.data.model.topheadlines.APIArticle
import com.amitverma.newsapp.di.ActivityScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import kotlinx.coroutines.flow.map

import javax.inject.Inject

@ActivityScope
class SearchRepository @Inject constructor(private val networkService: NetworkService) {

    fun getNewsByQueries(sources: String): Flow<List<APIArticle>> {
        return flow {
            emit(networkService.getNewsByQueries(sources))
        }.map {
            it.articles
        }
    }
}