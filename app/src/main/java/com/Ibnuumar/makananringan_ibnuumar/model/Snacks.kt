package com.Ibnuumar.makananringan_ibnuumar.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "snack_table")
data class Snacks (
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val name : String,
    val desc: String,
    val flavor : String
    ) :Parcelable

