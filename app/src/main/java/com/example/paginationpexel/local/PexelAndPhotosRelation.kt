package com.example.paginationpexel.local

import androidx.room.Embedded
import androidx.room.Relation

data class PexelAndPhotosRelation(
    @Embedded val pexelEntity: PexelEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "pexelId"
    ) val photos: List<PhotoEntity>
)