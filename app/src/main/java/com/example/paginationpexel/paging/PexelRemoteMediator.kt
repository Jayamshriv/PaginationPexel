package com.example.paginationpexel.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.paginationpexel.ApiService
import com.example.paginationpexel.dto.PexelDto
import com.example.paginationpexel.dto.Photo
import com.example.paginationpexel.dto.Src
import com.example.paginationpexel.local.PexelDao
import com.example.paginationpexel.local.PexelDatabase
import com.example.paginationpexel.local.PexelEntity
import com.example.paginationpexel.local.PhotoEntity
import com.example.paginationpexel.local.RemoteKeysEntity
import com.example.paginationpexel.local.SrcEntity
import com.example.paginationpexel.mappers.toPexelEntity
import com.example.paginationpexel.mappers.toPhotoEntity
import com.example.paginationpexel.mappers.toSrcEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPagingApi::class)
class PexelRemoteMediator(
    private val database: PexelDatabase,
    private val apiService: ApiService
) : RemoteMediator<Int, PexelEntity>() {

    companion object {
        const val FIRST_INDEX = 1
    }

    private val dao = database.dao

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PexelEntity>
    ): MediatorResult {
        try {
            val currentPage = when (loadType) {
                REFRESH -> FIRST_INDEX
                PREPEND -> {
                    val remoteKey = getFirstRemoteKey(state)
                    remoteKey?.prevPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKey != null
                    )
                }

                APPEND -> {
                    val remoteKey = getLastRemoteKey(state)
                    remoteKey?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKey != null
                    )
                }
            }

            val response = apiService.getPhotos(
                query = "nature",
                page = currentPage,
                per_page = state.config.pageSize
            )
            val endOfPaginationReached = response.photos.isEmpty()

            val prevPage = if (currentPage == FIRST_INDEX) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            database.withTransaction {
                if (loadType == REFRESH) {
                    dao.deleteAll()
                    dao.deleteAllRemoteKeys()
                }

                val remoteKeys = response.photos.map { photo ->
                    RemoteKeysEntity(
                        id = photo.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }

//                val photoEntities =
//                    response.photos.map { it.toPhotoEntity() }
//                val srcEntities = response.photos.map { it.src.toSrcEntity() }

                dao.insertAllRemoteKeys(remoteKeys)
                dao.insertPexel(response.toPexelEntity())
//                dao.insertPhotos(photoEntities)
//                srcEntities.forEach { dao.insertSrc(it) }
            }

            return MediatorResult.Success(endOfPaginationReached)

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private fun getFirstRemoteKey(state: PagingState<Int, PexelEntity>): RemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { dao.getRemoteKeys(it.id) }
    }

    private fun getLastRemoteKey(state: PagingState<Int, PexelEntity>): RemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { dao.getRemoteKeys(it.id) }
    }
}
