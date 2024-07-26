package com.example.paginationpexel.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


//@Entity(
//    tableName = "photosTable"
//)
data class PhotoEntity(
    @PrimaryKey val id: Int,
    val alt: String,
    val avg_color: String,
    val height: Int,
    val src: SrcEntity,
    val liked: Boolean,
    val photographer: String,
    val photographer_id: Int,
    val photographer_url: String,
    val url: String,
    val width: Int
)
