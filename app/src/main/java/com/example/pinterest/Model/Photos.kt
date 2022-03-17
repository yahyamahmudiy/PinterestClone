package com.example.pinterest.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class Photos(
    @PrimaryKey
    val id: String,
    val color: String,
    val description: String,
    val altDescription: String,
    val url:String,
    val word:String,
)