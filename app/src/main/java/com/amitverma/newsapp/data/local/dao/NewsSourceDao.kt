package com.amitverma.newsapp.data.local.dao

import androidx.room.*
import com.amitverma.newsapp.data.local.entity.NewsSource
import kotlinx.coroutines.flow.Flow


@Dao
interface NewsSourceDao {

    @Transaction
    @Query("SELECT * FROM NewsSource")
    fun getAllNewsSources(): Flow<List<NewsSource>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewsSources(newsSource: List<NewsSource>): List<Long>


    @Query("DELETE FROM NewsSource")
    fun clearNewsSources(): Int

    @Transaction
    suspend fun insertAndDeleteNewsSources(newsSource: List<NewsSource>): List<Long> {
        clearNewsSources()
        return insertNewsSources(newsSource)
    }

}