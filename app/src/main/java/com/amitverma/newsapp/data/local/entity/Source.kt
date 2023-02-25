package com.amitverma.newsapp.data.local.entity

import androidx.room.ColumnInfo


data class Source(
    @ColumnInfo(name = "sourceId") var sourceId: String = "",
    @ColumnInfo(name = "name") var name: String = ""
)
