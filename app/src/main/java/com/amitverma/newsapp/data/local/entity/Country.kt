package com.amitverma.newsapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Country")
data class Country(
    @PrimaryKey @ColumnInfo(name = "id") val id: String, @ColumnInfo(name = "name") val name: String
)
