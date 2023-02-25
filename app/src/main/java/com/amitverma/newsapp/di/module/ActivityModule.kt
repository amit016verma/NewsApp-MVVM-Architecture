package com.amitverma.newsapp.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.amitverma.newsapp.data.local.NewsAppDatabase
import com.amitverma.newsapp.data.repository.PagingTopHeadlineRepository
import com.amitverma.newsapp.data.repository.TopHeadlineRepository
import com.amitverma.newsapp.di.ActivityContext
import com.amitverma.newsapp.ui.base.ViewModelProviderFactory
import com.amitverma.newsapp.ui.pagination.PagingTopHeadlineAdapter
import com.amitverma.newsapp.ui.pagination.PagingTopHeadlineViewModel
import com.amitverma.newsapp.ui.topheadline.TopHeadlineAdapter
import com.amitverma.newsapp.ui.topheadline.TopHeadlineViewModel
import com.amitverma.newsapp.utils.DispatcherProvider
import com.amitverma.newsapp.utils.NetworkHelper
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @ActivityContext
    @Provides
    fun provideContext(): Context {
        return activity
    }

    @Provides
    fun provideTopHeadLinesViewModel(
        topHeadlineRepository: TopHeadlineRepository,
        networkHelper: NetworkHelper,
        dispatcherProvider: DispatcherProvider
    ): TopHeadlineViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(TopHeadlineViewModel::class) {
            TopHeadlineViewModel(
                topHeadlineRepository, networkHelper, dispatcherProvider
            )
        })[TopHeadlineViewModel::class.java]
    }

    @Provides
    fun providePagingTopHeadLinesViewModel(
        pagingTopHeadlineRepository: PagingTopHeadlineRepository,
        networkHelper: NetworkHelper
    ): PagingTopHeadlineViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(PagingTopHeadlineViewModel::class) {
                PagingTopHeadlineViewModel(
                    pagingTopHeadlineRepository, networkHelper
                )
            })[PagingTopHeadlineViewModel::class.java]
    }

    @Provides
    fun provideTopHeadlineAdapter() = TopHeadlineAdapter(ArrayList())

    @Provides
    fun providePagingTopHeadlineAdapter() = PagingTopHeadlineAdapter()

    @Provides
    fun provideTopHeadlinesDao(newsAppDatabase: NewsAppDatabase) = newsAppDatabase.topHeadlinesDao()


}