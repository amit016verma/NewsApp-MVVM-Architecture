package com.amitverma.newsapp.ui.pagination

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.amitverma.newsapp.data.model.topheadlines.APIArticle
import com.amitverma.newsapp.data.repository.PagingTopHeadlineRepository
import com.amitverma.newsapp.ui.base.BaseViewModel
import com.amitverma.newsapp.utils.AppConstant
import com.amitverma.newsapp.utils.NetworkHelper
import kotlinx.coroutines.flow.Flow

class PagingTopHeadlineViewModel(
    topHeadlineRepository: PagingTopHeadlineRepository, networkHelper: NetworkHelper
) : BaseViewModel<List<*>>(networkHelper) {

    val pagingDataFlow: Flow<PagingData<APIArticle>>

    init {
        pagingDataFlow = topHeadlineRepository.getTopHeadlines(country = AppConstant.COUNTRY)
            .cachedIn(viewModelScope)
    }

}