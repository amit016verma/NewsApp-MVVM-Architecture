package com.amitverma.newsapp.di.component

import com.amitverma.newsapp.di.ActivityScope
import com.amitverma.newsapp.di.module.ActivityModule
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent