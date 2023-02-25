package com.amitverma.newsapp

import android.app.Application
import com.amitverma.newsapp.di.component.ApplicationComponent
import com.amitverma.newsapp.di.component.DaggerApplicationComponent
import com.amitverma.newsapp.di.module.ApplicationModule

class NewsApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        applicationComponent =
            DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
        applicationComponent.inject(this)
    }

    // Needed to replace the component with a test specific one
    fun setTestComponent(applicationComponent: ApplicationComponent) {
        this.applicationComponent = applicationComponent
    }
}