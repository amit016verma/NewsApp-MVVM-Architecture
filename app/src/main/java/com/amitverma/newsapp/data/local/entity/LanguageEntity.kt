package com.amitverma.newsapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amitverma.newsapp.data.model.Language

@Entity(tableName = "Language")
data class LanguageEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: String = "",
    @ColumnInfo(name = "name") val name: String = ""
)

fun LanguageEntity.asLanguage() = Language(
    id = id, name = name
)
