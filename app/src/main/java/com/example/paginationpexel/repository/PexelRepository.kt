package com.example.paginationpexel.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.paginationpexel.ApiService
import com.example.paginationpexel.local.PexelDao
import com.example.paginationpexel.local.PexelDatabase
import com.example.paginationpexel.local.PexelEntity
import com.example.paginationpexel.paging.PexelRemoteMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


interface PexelRepository{
     fun getPexelPhotos(): Flow<PagingData<PexelEntity>>
}
