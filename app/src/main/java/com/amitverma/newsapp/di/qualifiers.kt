package com.amitverma.newsapp.di

import java.lang.annotation.Documented
import java.lang.annotation.RetentionPolicy

import javax.inject.Qualifier


@Qualifier
@Documented
@java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
annotation class DatabaseName

@Qualifier
@Documented
@java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
annotation class NetworkAPIKey

@Qualifier
@Documented
@java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
annotation class BASEURL