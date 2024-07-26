package com.example.paginationpexel.mappers

import com.example.paginationpexel.dto.PexelDto
import com.example.paginationpexel.dto.Photo
import com.example.paginationpexel.dto.Src
import com.example.paginationpexel.local.PexelEntity
import com.example.paginationpexel.local.PhotoEntity
import com.example.paginationpexel.local.SrcEntity

fun PexelDto.toPexelEntity(): PexelEntity {
    return PexelEntity(
        id = 0,
        next_page = this.next_page,
        page = this.page,
        per_page = this.per_page,
        photos = this.photos.map { it.toPhotoEntity() },
        prev_page = this.prev_page,
        total_results = this.total_results
    )
}

fun Photo.toPhotoEntity(): PhotoEntity {
    return PhotoEntity(
        id = this.id,
        alt = this.alt,
        avg_color = this.avg_color,
        height = this.height,
        liked = this.liked,
        photographer = this.photographer,
        photographer_id = this.photographer_id,
        photographer_url = this.photographer_url,
        src = this.src.toSrcEntity(),
        url = this.url,
        width = this.width
    )
}

fun Src.toSrcEntity(): SrcEntity {
    return SrcEntity(
        original = this.original,
    )
}
