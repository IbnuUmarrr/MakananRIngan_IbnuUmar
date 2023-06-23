package com.Ibnuumar.makananringan_ibnuumar.repository

import com.Ibnuumar.makananringan_ibnuumar.dao.SnacksDao
import com.Ibnuumar.makananringan_ibnuumar.model.Snacks
import kotlinx.coroutines.flow.Flow

class SnacksRepository (private val snacksDao: SnacksDao){
    val allsnacks : Flow<List<Snacks>> = snacksDao.getAllsnacks()

    suspend fun insertSnacks (snacks: Snacks) {
        snacksDao.insertsnacks(snacks)
    }
    suspend fun deleteSnacks (snacks: Snacks) {
        snacksDao.deletesnacks(snacks)
    }
    suspend fun updateSnacks (snacks: Snacks) {
        snacksDao.updatesnacks(snacks)
    }
}