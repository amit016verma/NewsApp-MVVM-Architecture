package com.amitverma.newsapp.di.module

import android.app.Application
import android.content.Context
import com.amitverma.newsapp.NewsApplication
import com.amitverma.newsapp.di.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: NewsApplication) {

    @Provides
    @Singleton
    fun provideApplication(): Application = application


    @ApplicationContext
    @Provides
    fun provideContext(): Context {
        return application
    }

}