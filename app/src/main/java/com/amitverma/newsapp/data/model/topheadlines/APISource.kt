package com.amitverma.newsapp.data.model.topheadlines

import com.amitverma.newsapp.data.local.entity.Source
import com.google.gson.annotations.SerializedName

data class APISource(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String = "",
)

fun APISource.asSourceEntity(): Source = Source(
    sourceId = id ?: "", name = name
)

fun APISource.asSourceIdEntity(sourceId: String): Source = Source(
    sourceId = sourceId, name = name
)
