package com.Ibnuumar.makananringan_ibnuumar.application

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.Ibnuumar.makananringan_ibnuumar.dao.SnacksDao
import com.Ibnuumar.makananringan_ibnuumar.model.Snacks

@Database(entities = [Snacks::class], version = 3, exportSchema = false)
abstract class SnacksDatabase : RoomDatabase() {
    abstract fun snacksDao(): SnacksDao

    companion object{
        private var INSTANCE : SnacksDatabase? = null
        private val migration1to2 : Migration =object :Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE snack_table ADD COLUMN latitude Double DEFAULT 0.0")
                database.execSQL("ALTER TABLE snack_table ADD COLUMN longitude Double DEFAULT 0.0")
            }
        }

        fun getDatabase(context : Context): SnacksDatabase{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SnacksDatabase::class.java,
                    "snacks_database"
                )
                    .addMigrations(migration1to2)
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}