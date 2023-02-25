package com.amitverma.newsapp.data.local.dao

import androidx.room.*
import com.amitverma.newsapp.data.local.entity.LanguageEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface LanguageDao {

    @Transaction
    @Query("SELECT * FROM Language")
    fun getLanguage(): Flow<List<LanguageEntity>>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLanguage(languages: List<LanguageEntity>): List<Long>

    @Transaction
    @Query("DELETE FROM Language")
    suspend fun clearLanguage()

}