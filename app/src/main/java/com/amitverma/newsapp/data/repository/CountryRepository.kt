package com.amitverma.newsapp.data.repository

import com.amitverma.newsapp.data.local.dao.CountryDao
import com.amitverma.newsapp.data.local.entity.Country
import com.amitverma.newsapp.di.ActivityScope
import com.amitverma.newsapp.utils.AppConstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityScope
class CountryRepository @Inject constructor(private val countryDao: CountryDao) {

    fun getCountries(): Flow<List<Country>> {
        return countryDao.getCountry()
    }

    suspend fun saveCountries(): Flow<List<Long>> {
        return flow {
            countryDao.clearCountry()
            emit(countryDao.insertCountry(AppConstant.COUNTRIES))
        }
    }
}