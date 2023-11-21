package com.jaresinunez.bitfitpt1

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodItemDao {
    @Query("SELECT * FROM food_item_table")
    fun getAll(): Flow<List<FoodEntity>>

    @Insert
    fun insertAll(articles: FoodEntity)

    @Query("DELETE FROM food_item_table")
    fun deleteAll()

    @Query("SELECT AVG(calories) as average FROM food_item_table")
    fun getAverageCals(): Flow<Int>

    @Query("SELECT MIN(calories) as minimum FROM food_item_table")
    fun getMinCals(): Flow<Int>

    @Query("SELECT MAX(calories) as maximum FROM food_item_table")
    fun getMaxCals(): Flow<Int>
}