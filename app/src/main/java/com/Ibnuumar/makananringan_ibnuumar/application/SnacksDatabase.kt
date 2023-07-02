package com.Ibnuumar.makananringan_ibnuumar.application

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.Ibnuumar.makananringan_ibnuumar.dao.SnacksDao
import com.Ibnuumar.makananringan_ibnuumar.model.Snacks

@Database(entities = [Snacks::class], version = 2, exportSchema = false)
abstract class SnacksDatabase : RoomDatabase() {
    abstract fun snacksDao(): SnacksDao

    companion object{
        private var INSTANCE : SnacksDatabase? = null

        fun getDatabase(context : Context): SnacksDatabase{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SnacksDatabase::class.java,
                    "snacks_database"
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}