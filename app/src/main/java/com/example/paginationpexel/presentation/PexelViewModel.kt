package com.example.paginationpexel.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.paginationpexel.local.PexelEntity
import com.example.paginationpexel.repository.PexelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class PexelViewModel @Inject constructor(
    private val repository: PexelRepository
) : ViewModel() {

    val pexelPhotos: Flow<PagingData<PexelEntity>> = repository.getPexelPhotos().cachedIn(viewModelScope)
}
