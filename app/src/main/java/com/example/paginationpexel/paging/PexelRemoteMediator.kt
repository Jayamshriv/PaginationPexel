package com.example.paginationpexel.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.paginationpexel.ApiService
import com.example.paginationpexel.Constants
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
                REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: FIRST_INDEX
                }
                PREPEND -> {
                    val remoteKey = getFirstRemoteKey(state)
                    Log.d(Constants.IV_LOG_TAG+" Prepend", "remoteKeysNext: ${remoteKey?.prevPage}")
                    remoteKey?.prevPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKey != null
                    )
                }

                APPEND -> {
                    val remoteKey = getLastRemoteKey(state)
                    Log.d(Constants.IV_LOG_TAG+" Append", "remoteKeysNext: ${remoteKey?.nextPage}")
                    remoteKey?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKey != null
                    )
                }
            }

            val response = apiService.getPhotos(
                query = "nature",
                page = currentPage,
                per_page = 20
            )
            val endOfPaginationReached = response.photos.isEmpty()
            Log.d(Constants.IV_LOG_TAG, "endOfPaginationReached: $endOfPaginationReached")

            val prevPage = if (currentPage == FIRST_INDEX) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            Log.d(Constants.IV_LOG_TAG, "prevPage: $prevPage")
            Log.d(Constants.IV_LOG_TAG, "nextPage: $nextPage")

            database.withTransaction {
                if (loadType == REFRESH) {
                    dao.deleteAll()
                    dao.deleteAllRemoteKeys()
                }
//                val remokey =

                val remoteKeys =
//                    response.photos.map {
                    RemoteKeysEntity(
                        id = response.page,
                        prevPage = prevPage,
                        nextPage = nextPage
                    ).also {
                        Log.d(Constants.IV_LOG_TAG, "Inserting RemoteKey: id=${it.id}, prevPage=${it.prevPage}, nextPage=${it.nextPage}")
                    }
//                }


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
            Log.d(Constants.IV_LOG_TAG, "LoadResultError: ${e.message}")
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, PexelEntity>): RemoteKeysEntity? {
        val close = state
            .anchorPosition
            ?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                dao.getRemoteKeys(id = id)
            }
        }
        Log.d(Constants.IV_LOG_TAG, "Closest : $close")

        return close
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, PexelEntity>): RemoteKeysEntity? {
        val first
        = state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()
            ?.let {
                dao.getRemoteKeys(it.page)
            }
        Log.d(Constants.IV_LOG_TAG, "First : $first")
        return first
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, PexelEntity>): RemoteKeysEntity? {
        val last = state.pages.lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { dao.getRemoteKeys(it.page) }
        Log.d(Constants.IV_LOG_TAG, "last : $last")
        return last
    }
}
