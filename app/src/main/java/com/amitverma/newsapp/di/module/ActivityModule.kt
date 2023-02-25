package com.amitverma.newsapp.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.amitverma.newsapp.data.local.NewsAppDatabase
import com.amitverma.newsapp.data.repository.*
import com.amitverma.newsapp.di.ActivityContext
import com.amitverma.newsapp.ui.base.ViewModelProviderFactory
import com.amitverma.newsapp.ui.country.CountriesListAdapter
import com.amitverma.newsapp.ui.country.CountryListViewModel
import com.amitverma.newsapp.ui.language.LanguageListAdapter
import com.amitverma.newsapp.ui.language.LanguageListViewModel
import com.amitverma.newsapp.ui.newsListScreen.NewsListAdapter
import com.amitverma.newsapp.ui.newsListScreen.NewsListViewModel
import com.amitverma.newsapp.ui.pagination.PagingTopHeadlineAdapter
import com.amitverma.newsapp.ui.pagination.PagingTopHeadlineViewModel
import com.amitverma.newsapp.ui.search.SearchViewModel
import com.amitverma.newsapp.ui.sources.NewsSourcesListAdapter
import com.amitverma.newsapp.ui.sources.SourcesViewModel
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
        pagingTopHeadlineRepository: PagingTopHeadlineRepository, networkHelper: NetworkHelper
    ): PagingTopHeadlineViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(PagingTopHeadlineViewModel::class) {
                PagingTopHeadlineViewModel(
                    pagingTopHeadlineRepository, networkHelper
                )
            })[PagingTopHeadlineViewModel::class.java]
    }

    @Provides
    fun provideSourcesViewModel(
        newsSourceRepository: NewsSourceRepository,
        networkHelper: NetworkHelper,
        dispatcherProvider: DispatcherProvider
    ): SourcesViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(SourcesViewModel::class) {
            SourcesViewModel(newsSourceRepository, networkHelper, dispatcherProvider)
        })[SourcesViewModel::class.java]
    }


    @Provides
    fun provideNewsListViewModel(
        newsRepository: NewsRepository,
        networkHelper: NetworkHelper,
        dispatcherProvider: DispatcherProvider
    ): NewsListViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(NewsListViewModel::class) {
            NewsListViewModel(newsRepository, networkHelper, dispatcherProvider)
        })[NewsListViewModel::class.java]
    }

    @Provides
    fun provideCountryListViewModel(
        countryRepository: CountryRepository,
        networkHelper: NetworkHelper,
        dispatcherProvider: DispatcherProvider
    ): CountryListViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(CountryListViewModel::class) {
            CountryListViewModel(countryRepository, networkHelper, dispatcherProvider)
        })[CountryListViewModel::class.java]
    }

    @Provides
    fun provideLanguageListViewModel(
        languageRepository: LanguageRepository,
        networkHelper: NetworkHelper,
        dispatcherProvider: DispatcherProvider
    ): LanguageListViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(LanguageListViewModel::class) {
            LanguageListViewModel(languageRepository, networkHelper, dispatcherProvider)
        })[LanguageListViewModel::class.java]
    }

    @Provides
    fun provideSearchViewModel(
        searchRepository: SearchRepository,
        networkHelper: NetworkHelper,
        dispatcherProvider: DispatcherProvider
    ): SearchViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(SearchViewModel::class) {
            SearchViewModel(searchRepository, networkHelper, dispatcherProvider)
        })[SearchViewModel::class.java]
    }


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

    @Provides
    fun provideTopHeadlinesDao(newsAppDatabase: NewsAppDatabase) = newsAppDatabase.topHeadlinesDao()

    @Provides
    fun provideNewsSourceDao(newsAppDatabase: NewsAppDatabase) = newsAppDatabase.newsSourceDao()

    @Provides
    fun provideLanguageDao(newsAppDatabase: NewsAppDatabase) = newsAppDatabase.languageDao()

    @Provides
    fun provideCountryDao(newsAppDatabase: NewsAppDatabase) = newsAppDatabase.countryDao()


}