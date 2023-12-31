package com.amitverma.newsapp.data.repository

import com.amitverma.newsapp.data.local.dao.LanguageDao
import com.amitverma.newsapp.data.local.entity.LanguageEntity
import com.amitverma.newsapp.di.ActivityScope
import com.amitverma.newsapp.utils.AppConstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityScope
class LanguageRepository @Inject constructor(private val languageDao: LanguageDao) {

    fun getLanguages(): Flow<List<LanguageEntity>> {
        return languageDao.getLanguage()
    }

    fun saveLanguage(): Flow<List<Long>> {
        return flow {
            languageDao.clearLanguage()
            emit(languageDao.insertLanguage(AppConstant.LANGUAGES))
        }
    }

}