package com.Ibnuumar.makananringan_ibnuumar.application

import android.app.Application
import com.Ibnuumar.makananringan_ibnuumar.repository.SnacksRepository

class SnacksApp: Application() {
val database by lazy { SnacksDatabase.getDatabase(this) }
    val repository by lazy { SnacksRepository(database.snacksDao()) }
}