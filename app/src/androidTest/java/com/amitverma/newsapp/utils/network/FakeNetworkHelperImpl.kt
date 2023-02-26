package com.amitverma.newsapp.utils.network

import android.content.Context
import com.amitverma.newsapp.di.ApplicationContext
import com.amitverma.newsapp.utils.NetworkHelper

class FakeNetworkHelperImpl(@ApplicationContext val context: Context) : NetworkHelper {

    private var status : Boolean = true

    override fun isNetworkConnected(): Boolean {
        return status
    }

    override fun castToNetworkError(throwable: Throwable): NetworkError {
        return NetworkHelperImpl(context).castToNetworkError(throwable)
    }

    override fun setStatus(status: Boolean) {
        this.status = status
    }

}