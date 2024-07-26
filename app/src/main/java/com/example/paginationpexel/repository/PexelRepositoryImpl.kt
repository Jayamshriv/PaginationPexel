package com.example.paginationpexel.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.paginationpexel.ApiService
import com.example.paginationpexel.local.PexelDatabase
import com.example.paginationpexel.local.PexelEntity
import com.example.paginationpexel.paging.PexelRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PexelRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val database: PexelDatabase
) : PexelRepository{
    @OptIn(ExperimentalPagingApi::class)
    override fun getPexelPhotos(): Flow<PagingData<PexelEntity>> {
        val pagingSourceFactory = { database.dao.pagingSource() }

        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            remoteMediator = PexelRemoteMediator(database, apiService),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}
