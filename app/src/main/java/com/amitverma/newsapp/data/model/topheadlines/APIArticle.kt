package com.amitverma.newsapp.data.model.topheadlines

import com.amitverma.newsapp.data.local.entity.Article
import com.google.gson.annotations.SerializedName

data class APIArticle(
    @SerializedName("title") val title: String = "",
    @SerializedName("description") val description: String? = "",
    @SerializedName("url") val url: String = "",
    @SerializedName("urlToImage") val imageUrl: String? = "",
    @SerializedName("source") val source: APISource
)

fun APIArticle.asEntity(country: String) = Article(
    title = title,
    description = description ?: "",
    url = url,
    imageUrl = imageUrl ?: "",
    country = country,
    source = source.asSourceEntity()
)

fun APIArticle.asSourceIdEntity(sourceId: String) = Article(
    title = title,
    description = description ?: "",
    url = url,
    imageUrl = imageUrl ?: "",
    source = source.asSourceIdEntity(sourceId)
)

fun APIArticle.asLanguageEntity(language: String) = Article(
    title = title,
    description = description ?: "",
    url = url,
    imageUrl = imageUrl ?: "",
    language = language,
    source = source.asSourceEntity()
)
