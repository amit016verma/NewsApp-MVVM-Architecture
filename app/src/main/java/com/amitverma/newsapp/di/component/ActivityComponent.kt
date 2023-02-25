package com.amitverma.newsapp.di.component

import com.amitverma.newsapp.data.repository.*
import com.amitverma.newsapp.di.ActivityScope
import com.amitverma.newsapp.di.module.ActivityModule
import com.amitverma.newsapp.ui.country.CountryListActivity
import com.amitverma.newsapp.ui.newsListScreen.NewsListActivity
import com.amitverma.newsapp.ui.pagination.PaginationTopHeadlineActivity
import com.amitverma.newsapp.ui.sources.SourcesActivity
import com.amitverma.newsapp.ui.topheadline.TopHeadlineActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: TopHeadlineActivity)

    fun inject(activity: PaginationTopHeadlineActivity)

    fun inject(activity: SourcesActivity)

    fun inject(activity: NewsListActivity)

    fun inject(activity: CountryListActivity)

    fun getTopHeadlineRepository(): TopHeadlineRepository

    fun getPagingTopHeadlineRepository(): PagingTopHeadlineRepository

    fun getNewsSourceRepository(): NewsSourceRepository

    fun getNewsRepository(): NewsRepository

    fun getLanguageRepository(): LanguageRepository

    fun getCountryRepository(): CountryRepository
}