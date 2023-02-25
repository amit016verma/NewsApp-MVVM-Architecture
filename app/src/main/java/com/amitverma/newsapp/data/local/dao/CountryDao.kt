package com.amitverma.newsapp.data.local.dao

import androidx.room.*
import com.amitverma.newsapp.data.local.entity.Country
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao {

    @Transaction
    @Query("SELECT * FROM Country")
    fun getCountry(): Flow<List<Country>>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(countries: List<Country>): List<Long>

    @Transaction
    @Query("DELETE FROM Country")
    suspend fun clearCountry()

}