package com.amitverma.newsapp.data.model.newssources

import com.amitverma.newsapp.data.local.entity.NewsSource
import com.google.gson.annotations.SerializedName

data class APINewsSource(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("url") val url: String = "",
    @SerializedName("category") val category: String = "",
    @SerializedName("language") val language: String = "",
    @SerializedName("country") val country: String = "",
)

fun APINewsSource.asEntity() = NewsSource(
    sourceId = id ?: "",
    name = name,
    description = description,
    url = url,
    category = category,
    language = language,
    country = country
)
