package com.amitverma.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amitverma.newsapp.data.local.dao.CountryDao
import com.amitverma.newsapp.data.local.dao.LanguageDao
import com.amitverma.newsapp.data.local.dao.NewsSourceDao
import com.amitverma.newsapp.data.local.dao.TopHeadlinesDao
import com.amitverma.newsapp.data.local.entity.Article
import com.amitverma.newsapp.data.local.entity.Country
import com.amitverma.newsapp.data.local.entity.LanguageEntity
import com.amitverma.newsapp.data.local.entity.NewsSource

@Database(
    entities = [Article::class, NewsSource::class, LanguageEntity::class, Country::class],
    version = 1,
    exportSchema = false
)
abstract class NewsAppDatabase : RoomDatabase() {

    abstract fun topHeadlinesDao(): TopHeadlinesDao

    abstract fun newsSourceDao(): NewsSourceDao

    abstract fun languageDao(): LanguageDao

    abstract fun countryDao(): CountryDao

}