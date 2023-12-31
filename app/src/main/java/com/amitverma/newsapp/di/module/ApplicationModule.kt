package com.amitverma.newsapp.di.module

import android.content.Context
import androidx.room.Room
import com.amitverma.newsapp.BuildConfig
import com.amitverma.newsapp.data.api.AuthTokenInterceptor
import com.amitverma.newsapp.data.api.NetworkService
import com.amitverma.newsapp.data.local.NewsAppDatabase
import com.amitverma.newsapp.di.BASEURL
import com.amitverma.newsapp.di.DatabaseName
import com.amitverma.newsapp.di.NetworkAPIKey
import com.amitverma.newsapp.utils.DefaultDispatcherProvider
import com.amitverma.newsapp.utils.DispatcherProvider
import com.amitverma.newsapp.utils.NetworkHelper
import com.amitverma.newsapp.utils.network.NetworkHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun provideNetworkService(
        @BASEURL baseUrl: String,
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): NetworkService = Retrofit.Builder().baseUrl(baseUrl).client(
        okHttpClient
    ).addConverterFactory(gsonConverterFactory).build().create(NetworkService::class.java)

    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor, authTokenInterceptor: AuthTokenInterceptor
    ): OkHttpClient = OkHttpClient().newBuilder().addInterceptor(authTokenInterceptor)
        .addInterceptor(httpLoggingInterceptor).build()

    @Provides
    fun provideAuthTokenInterceptor(@NetworkAPIKey apiKey: String): AuthTokenInterceptor =
        AuthTokenInterceptor(apiKey)

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return httpLoggingInterceptor
    }

    @Provides
    fun provideNewsAppDatabase(
        @ApplicationContext context: Context,
        @DatabaseName databaseName: String
    ): NewsAppDatabase =
        Room.databaseBuilder(
            context, NewsAppDatabase::class.java, databaseName
        ).build()

    @Provides
    @Singleton
    @DatabaseName
    fun provideDatabaseName(): String = "news_app_database"

    @Provides
    @Singleton
    fun provideNetworkConnection(@ApplicationContext context: Context): NetworkHelper =
        NetworkHelperImpl(context)

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()

    @Provides
    @Singleton
    @NetworkAPIKey
    fun provideNetWorkAPIKey(): String = "7522d3bfb4d847fd94a529f3a08731f3"

    @Provides
    @Singleton
    @BASEURL
    fun provideNetWorkBaseUrl(): String = "https://newsapi.org/v2/"


    @Provides
    fun provideTopHeadlinesDao(newsAppDatabase: NewsAppDatabase) = newsAppDatabase.topHeadlinesDao()

    @Provides
    fun provideNewsSourceDao(newsAppDatabase: NewsAppDatabase) = newsAppDatabase.newsSourceDao()

    @Provides
    fun provideLanguageDao(newsAppDatabase: NewsAppDatabase) = newsAppDatabase.languageDao()

    @Provides
    fun provideCountryDao(newsAppDatabase: NewsAppDatabase) = newsAppDatabase.countryDao()


}