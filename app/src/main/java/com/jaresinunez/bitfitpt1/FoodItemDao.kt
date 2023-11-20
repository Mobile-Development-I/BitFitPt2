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
}