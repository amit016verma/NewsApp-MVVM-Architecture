package com.amitverma.newsapp.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.amitverma.newsapp.BuildConfig
import com.amitverma.newsapp.NewsApplication
import com.amitverma.newsapp.data.api.AuthTokenInterceptor
import com.amitverma.newsapp.data.api.FakeNetworkService
import com.amitverma.newsapp.data.api.NetworkService
import com.amitverma.newsapp.data.local.NewsAppDatabase
import com.amitverma.newsapp.di.BASEURL
import com.amitverma.newsapp.di.DatabaseName
import com.amitverma.newsapp.di.NetworkAPIKey
import com.amitverma.newsapp.utils.DefaultDispatcherProvider
import com.amitverma.newsapp.utils.DispatcherProvider
import com.amitverma.newsapp.utils.NetworkHelper
import com.amitverma.newsapp.utils.network.FakeNetworkHelperImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationTestModule(private val application: NewsApplication) {

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    //    @ApplicationContext
    @Provides
    fun provideContext(): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideNetworkService(
        @BASEURL baseUrl: String,
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): NetworkService = FakeNetworkService()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor, authTokenInterceptor: AuthTokenInterceptor
    ): OkHttpClient = OkHttpClient().newBuilder().addInterceptor(authTokenInterceptor)
        .addInterceptor(httpLoggingInterceptor).build()

    @Provides
    @Singleton
    fun provideAuthTokenInterceptor(@NetworkAPIKey apiKey: String): AuthTokenInterceptor =
        AuthTokenInterceptor(apiKey)

    @Provides
    @Singleton
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
    @Singleton
    fun provideNewsAppDatabase(@DatabaseName databaseName: String): NewsAppDatabase =
        Room.databaseBuilder(
            application, NewsAppDatabase::class.java, databaseName
        ).build()

    @Provides
    @Singleton
    @DatabaseName
    fun provideDatabaseName(): String = "news_app_database"

    @Provides
    @Singleton
    fun provideNetworkConnection(context: Context): NetworkHelper = FakeNetworkHelperImpl(context)

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
}