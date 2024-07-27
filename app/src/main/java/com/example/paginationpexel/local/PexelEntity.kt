package com.example.paginationpexel.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pexelTable")
data class PexelEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val next_page: String?,
    val page: Int,
    val per_page: Int,
    val photos: List<PhotoEntity>,
    val prev_page: String?,
    val total_results: Int
)