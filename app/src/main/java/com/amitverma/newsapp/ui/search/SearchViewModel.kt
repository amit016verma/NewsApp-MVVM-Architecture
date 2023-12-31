package com.amitverma.newsapp.ui.search

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.amitverma.newsapp.data.model.topheadlines.APIArticle
import com.amitverma.newsapp.data.repository.SearchRepository
import com.amitverma.newsapp.ui.base.BaseViewModel
import com.amitverma.newsapp.utils.DispatcherProvider
import com.amitverma.newsapp.utils.NetworkHelper
import com.amitverma.newsapp.utils.Resource
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchRepository: SearchRepository,
    networkHelper: NetworkHelper,
    private val dispatcherProvider: DispatcherProvider
) : BaseViewModel<List<*>>(networkHelper) {


    @OptIn(FlowPreview::class)
    fun setUpSearchStateFlow(searchFlow: StateFlow<String>) {
        viewModelScope.launch(dispatcherProvider.main) {
            searchFlow.debounce(2000).filter { query ->
                if (query.isEmpty()) {
                    _data.value = Resource.success(listOf<APIArticle>())
                    return@filter false
                } else {
                    return@filter true
                }
            }.distinctUntilChanged().flatMapLatest { query ->
                Log.d("query", query)
                searchRepository.getNewsByQueries(sources = query).catch { e ->
                    _data.value = Resource.error(e.toString())
                }
            }.flowOn(dispatcherProvider.io).collect {
                _data.value = Resource.success(it)
            }
        }
    }

    fun fetchNewsByQueries(queries: String) {
        viewModelScope.launch(dispatcherProvider.main) {
            searchRepository.getNewsByQueries(sources = queries).catch { e ->
                _data.value = Resource.error(e.toString())
            }.flowOn(dispatcherProvider.io).collect {
                _data.value = Resource.success(it)
            }
        }
    }

}