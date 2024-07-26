package com.example.paginationpexel.local

import androidx.room.Embedded
import androidx.room.Relation

data class PhotosAndSrcRelation(
    @Embedded val photoEntity: PhotoEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "photoId"
    )val src : SrcEntity?
)
