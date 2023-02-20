package com.amitverma.newsapp.di.component

import android.content.Context
import com.amitverma.newsapp.NewsApplication
import com.amitverma.newsapp.di.ApplicationContext
import com.amitverma.newsapp.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: NewsApplication)

    @ApplicationContext
    fun getApplicationContext(): Context
}