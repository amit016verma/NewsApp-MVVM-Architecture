package com.amitverma.newsapp.di.component

import android.content.Context
import com.amitverma.newsapp.NewsApplication
import com.amitverma.newsapp.data.api.NetworkService
import com.amitverma.newsapp.data.local.NewsAppDatabase
import com.amitverma.newsapp.di.ApplicationContext
import com.amitverma.newsapp.di.module.ApplicationModule
import com.amitverma.newsapp.utils.DispatcherProvider
import com.amitverma.newsapp.utils.NetworkHelper
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: NewsApplication)

    @ApplicationContext
    fun getApplicationContext(): Context

    fun getNetworkService(): NetworkService

    fun getNewsAppDatabase(): NewsAppDatabase

    fun getNetworkHelper(): NetworkHelper

    fun getDispatcherProvider(): DispatcherProvider
}