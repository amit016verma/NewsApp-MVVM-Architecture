package com.amitverma.newsapp.data.model.topheadlines

import com.google.gson.annotations.SerializedName

data class TopHeadlinesResponse(
    @SerializedName("status") val status: String = "",
    @SerializedName("totalResults") val count: Int = 0,
    @SerializedName("articles") val articles: List<APIArticle> = ArrayList(),
)
