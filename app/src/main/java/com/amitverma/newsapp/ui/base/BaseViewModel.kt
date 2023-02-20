package com.amitverma.newsapp.ui.base


import androidx.lifecycle.ViewModel
import com.amitverma.newsapp.utils.NetworkHelper
import com.amitverma.newsapp.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<T>(
    private val networkHelper: NetworkHelper
) : ViewModel() {

    protected val _data = MutableStateFlow<Resource<T>>(Resource.loading())

    val data: StateFlow<Resource<T>> = _data

    protected fun checkInternetConnection(): Boolean = networkHelper.isNetworkConnected()
}