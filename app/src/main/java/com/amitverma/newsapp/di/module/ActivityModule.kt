package com.amitverma.newsapp.di.module

import com.amitverma.newsapp.data.local.NewsAppDatabase
import com.amitverma.newsapp.ui.country.CountriesListAdapter
import com.amitverma.newsapp.ui.language.LanguageListAdapter
import com.amitverma.newsapp.ui.newsListScreen.NewsListAdapter
import com.amitverma.newsapp.ui.pagination.PagingTopHeadlineAdapter
import com.amitverma.newsapp.ui.sources.NewsSourcesListAdapter
import com.amitverma.newsapp.ui.topheadline.TopHeadlineAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    fun provideLanguageListAdapter() = LanguageListAdapter(ArrayList())

    @Provides
    fun provideCountriesListAdapter() = CountriesListAdapter(ArrayList())

    @Provides
    fun provideNewsSourcesListAdapter() = NewsSourcesListAdapter(ArrayList())

    @Provides
    fun provideTopHeadlineAdapter() = TopHeadlineAdapter(ArrayList())

    @Provides
    fun providePagingTopHeadlineAdapter() = PagingTopHeadlineAdapter()

    @Provides
    fun provideNewsListAdapter() = NewsListAdapter(ArrayList())


}