package com.Ibnuumar.makananringan_ibnuumar.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.Ibnuumar.makananringan_ibnuumar.model.Snacks
import kotlinx.coroutines.flow.Flow

@Dao
interface SnacksDao {
    @Query("Select * from snack_table order by name asc")
    fun getAllsnacks(): Flow<List<Snacks>>
    @Insert
    suspend fun insertsnacks(snacks: Snacks)
    @Delete
    suspend fun deletesnacks (snacks: Snacks)
    @Update fun updatesnacks (snacks: Snacks)
}