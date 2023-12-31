package com.amitverma.newsapp.ui.country

import androidx.lifecycle.viewModelScope
import com.amitverma.newsapp.data.repository.CountryRepository
import com.amitverma.newsapp.ui.base.BaseViewModel
import com.amitverma.newsapp.utils.DispatcherProvider
import com.amitverma.newsapp.utils.NetworkHelper
import com.amitverma.newsapp.utils.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class CountryListViewModel(
    private val countryRepository: CountryRepository,
    networkHelper: NetworkHelper,
    private val dispatcherProvider: DispatcherProvider
) : BaseViewModel<List<*>>(networkHelper) {

    init {
        saveCountries()
    }

    private fun saveCountries() {
        viewModelScope.launch(dispatcherProvider.main) {
            countryRepository.saveCountries().flowOn(dispatcherProvider.io).catch { e ->
                println("Exception :::$e")
                _data.value = Resource.error(e.toString())
            }.collect {
                println("saveCountries::$it")
                fetchCountries()
            }
        }
    }

    private fun fetchCountries() {
        viewModelScope.launch(dispatcherProvider.main) {
            countryRepository.getCountries().flowOn(dispatcherProvider.io).catch { e ->
                println("Exception :::$e")
                _data.value = Resource.error(e.toString())
            }.collect { countryList ->

                _data.value = Resource.success(countryList.sortedBy { it.name })
            }

        }
    }
}