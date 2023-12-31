package com.amitverma.newsapp.ui.newsListScreen

import androidx.lifecycle.viewModelScope
import com.amitverma.newsapp.data.local.entity.Article
import com.amitverma.newsapp.data.model.topheadlines.asEntity
import com.amitverma.newsapp.data.model.topheadlines.asLanguageEntity
import com.amitverma.newsapp.data.model.topheadlines.asSourceIdEntity
import com.amitverma.newsapp.data.repository.NewsRepository
import com.amitverma.newsapp.ui.base.BaseViewModel
import com.amitverma.newsapp.utils.DispatcherProvider
import com.amitverma.newsapp.utils.NetworkHelper
import com.amitverma.newsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    networkHelper: NetworkHelper,
    private val dispatcherProvider: DispatcherProvider
) : BaseViewModel<List<*>>(networkHelper) {


    fun fetchNewsBySources(sources: String) {
        if (checkInternetConnection()) fetchNewsBySourcesByNetwork(sources)
        else fetchNewsBySourcesByDB(sources)
    }

    @OptIn(FlowPreview::class)
    private fun fetchNewsBySourcesByNetwork(sources: String) {
        viewModelScope.launch(dispatcherProvider.main) {
            newsRepository.getNewsBySources(sources = sources).map {
                it.map { articleAPI -> articleAPI.asSourceIdEntity(sources) }
            }.flatMapConcat {
                return@flatMapConcat newsRepository.insertNewsBySources(
                    sourceID = sources, articles = it
                )
            }.flowOn(dispatcherProvider.io).catch { e ->
                _data.value = Resource.error(e.toString())
            }.collect {
                fetchNewsBySourcesByDB(sources)
            }
        }
    }

    private fun fetchNewsBySourcesByDB(sources: String) {
        viewModelScope.launch(dispatcherProvider.main) {
            newsRepository.getNewsSourceArticleByDB(sources).catch { e ->
                _data.value = Resource.error(e.toString())
            }.flowOn(dispatcherProvider.io).collect {
                if (!checkInternetConnection() && it.isEmpty()) {
                    _data.value = Resource.error("Data Not found.")
                } else {
                    _data.value = Resource.success(it)
                }
            }
        }
    }

    fun fetchNewsByCountry(country: String) {
        if (checkInternetConnection()) fetchNewsByCountryFromNetwork(country)
        else getNewsByCountry(country)
    }

    @OptIn(FlowPreview::class)
    private fun fetchNewsByCountryFromNetwork(country: String) {
        viewModelScope.launch(dispatcherProvider.main) {
            newsRepository.getTopHeadlines(country = country).map {
                it.map { articleAPI -> articleAPI.asEntity(country) }
            }.flatMapConcat {
                return@flatMapConcat newsRepository.insertNewsByCountry(country, it)
            }.flowOn(dispatcherProvider.io).catch { e ->
                _data.value = Resource.error(e.toString())
            }.collect {
                getNewsByCountry(country)
            }
        }
    }

    private fun getNewsByCountry(country: String) {
        viewModelScope.launch(dispatcherProvider.main) {
            newsRepository.getNewsByCountry(country).catch { e ->
                _data.value = Resource.error(e.toString())
            }.flowOn(dispatcherProvider.io).collect {
                if (!checkInternetConnection() && it.isEmpty()) {
                    _data.value = Resource.error("Data Not found.")
                } else {
                    _data.value = Resource.success(it)
                }
            }
        }
    }

    fun fetchNewsByLanguage(languageCode: String, secLanguageCode: String) {
        if (checkInternetConnection()) fetchNewsByLanguageAPI(
            languageCode, secLanguageCode
        )
        else fetchNewsByLanguageFromDB(languageCode, secLanguageCode)
    }

    @OptIn(FlowPreview::class)
    private fun fetchNewsByLanguageAPI(languageCode: String, secLanguageCode: String) {
        viewModelScope.launch(dispatcherProvider.main) {
            newsRepository.getNewsByLanguage(languageCode = languageCode)
                .zip(newsRepository.getNewsByLanguage(languageCode = secLanguageCode)) { firstLangRes, secLangRes ->
                    return@zip Pair(firstLangRes, secLangRes)
                }.flatMapConcat {
                    return@flatMapConcat newsRepository.insertNewsByLanguage(languageCode,
                        it.first.map { articleAPI -> articleAPI.asLanguageEntity(languageCode) })
                        .zip(
                            newsRepository.insertNewsByLanguage(secLanguageCode,
                                it.second.map { articleAPI ->
                                    articleAPI.asLanguageEntity(
                                        secLanguageCode
                                    )
                                })
                        ) { _, _ ->
                            return@zip true
                        }
                }.flowOn(dispatcherProvider.io).catch { e ->
                    _data.value = Resource.error(e.toString())
                }.collect {
                    fetchNewsByLanguageFromDB(languageCode, secLanguageCode)
                }
        }
    }

    private fun fetchNewsByLanguageFromDB(languageCode: String, secLanguageCode: String) {
        viewModelScope.launch(dispatcherProvider.main) {
            newsRepository.fetchNewsByArticleByLanguage(languageCode)
                .zip(newsRepository.fetchNewsByArticleByLanguage(secLanguageCode)) { firstLangData, secLangData ->
                    val articles = mutableListOf<Article>()
                    articles.addAll(firstLangData)
                    articles.addAll(secLangData)
                    return@zip articles
                }.catch { e ->
                    _data.value = Resource.error(e.toString())
                }.flowOn(dispatcherProvider.io).collect {
                    if (!checkInternetConnection() && it.isEmpty()) {
                        _data.value = Resource.error("Data Not found.")
                    } else {
                        _data.value = Resource.success(it)
                    }
                }
        }
    }
}