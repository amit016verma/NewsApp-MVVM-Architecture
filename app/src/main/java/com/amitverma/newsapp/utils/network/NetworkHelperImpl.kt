package com.amitverma.newsapp.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log

import com.amitverma.newsapp.utils.NetworkHelper
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException


class NetworkHelperImpl constructor(@ApplicationContext val context: Context) : NetworkHelper {

    companion object {
        private const val TAG = "NetworkHelper"
    }

    override fun isNetworkConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork?.isConnected ?: false
    }

    override fun castToNetworkError(throwable: Throwable): NetworkError {

        val defaultNetworkError = NetworkError()
        try {
            if (throwable is ConnectException) return NetworkError(0, "0")
            if (throwable !is HttpException) return defaultNetworkError
            val error = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
                .fromJson(throwable.response()?.errorBody()?.string(), NetworkError::class.java)
            return NetworkError(throwable.code(), error.statusCode, error.message)
        } catch (e: IOException) {
            Log.e(TAG, e.toString())
        } catch (e: JsonSyntaxException) {
            Log.e(TAG, e.toString())
        } catch (e: NullPointerException) {
            Log.e(TAG, e.toString())
        }
        return defaultNetworkError
    }

    override fun setStatus(status: Boolean) {

    }
}