package com.amitverma.newsapp.di.component

import com.amitverma.newsapp.data.repository.PagingTopHeadlineRepository
import com.amitverma.newsapp.data.repository.TopHeadlineRepository
import com.amitverma.newsapp.di.ActivityScope
import com.amitverma.newsapp.di.module.ActivityModule
import com.amitverma.newsapp.ui.pagination.PaginationTopHeadlineActivity
import com.amitverma.newsapp.ui.topheadline.TopHeadlineActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: TopHeadlineActivity)

    fun inject(activity: PaginationTopHeadlineActivity)

    fun getTopHeadlineRepository(): TopHeadlineRepository

    fun getPagingTopHeadlineRepository(): PagingTopHeadlineRepository
}