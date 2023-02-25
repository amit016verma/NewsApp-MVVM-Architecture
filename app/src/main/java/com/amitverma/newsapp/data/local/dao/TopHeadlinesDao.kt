package com.amitverma.newsapp.data.local.dao

import androidx.room.*
import com.amitverma.newsapp.data.local.entity.Article
import kotlinx.coroutines.flow.Flow


@Dao
interface TopHeadlinesDao {

    @Transaction
    @Query("SELECT * FROM TopHeadlinesArticle WHERE country =:country")
    fun getAllTopHeadlinesArticle(country: String): Flow<List<Article>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticles(articles: List<Article>): List<Long>

    @Query("DELETE FROM TopHeadlinesArticle WHERE country = :country ")
    fun clearTopHeadlinesArticles(country: String)

    @Query("DELETE FROM TopHeadlinesArticle WHERE sourceId = :sourceId ")
    fun clearSourceArticles(sourceId: String)


    @Transaction
    @Query("SELECT * FROM TopHeadlinesArticle WHERE sourceId =:sourceId")
    fun getNewsSourceArticle(sourceId: String): Flow<List<Article>>

    @Transaction
    suspend fun insertAndDeleteTopHeadlineArticles(
        country: String, articles: List<Article>
    ): List<Long> {
        clearTopHeadlinesArticles(country)
        return insertArticles(articles)
    }

    @Transaction
    suspend fun insertAndDeleteSourceArticles(
        sourceId: String, articles: List<Article>
    ): List<Long> {
        clearSourceArticles(sourceId)
        return insertArticles(articles)
    }

    @Transaction
    suspend fun insertAndDeleteLanguageArticles(
        language: String, articles: List<Article>
    ): List<Long> {
        clearLanguageArticles(language)
        return insertArticles(articles)
    }

    @Query("DELETE FROM TopHeadlinesArticle WHERE language = :language ")
    fun clearLanguageArticles(language: String)

    @Transaction
    @Query("SELECT * FROM TopHeadlinesArticle WHERE language =:languageCode")
    fun getNewsLanguageArticle(languageCode: String): Flow<List<Article>>

}