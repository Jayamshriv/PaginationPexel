package com.example.paginationpexel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.paginationpexel.local.PexelEntity
//import com.example.paginationpexel.presentation.HomeScreen
import com.example.paginationpexel.presentation.PexelViewModel
import com.example.paginationpexel.ui.theme.PaginationPexelTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PaginationPexelTheme {
                    MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: PexelViewModel = hiltViewModel() ) {
    PexelPhotoList(viewModel = viewModel)
}

@Composable
fun PexelPhotoList(viewModel: PexelViewModel) {
    val pexelPhotos = viewModel.pexelPhotos.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(pexelPhotos.itemCount) { index ->
            val photo = pexelPhotos[index]
            photo?.let {
                PexelPhotoItem(photo = it)
            }
        }

        pexelPhotos.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { CircularProgressIndicator(modifier = Modifier.fillMaxSize()) }
                }
                loadState.append is LoadState.Loading -> {
                    item { CircularProgressIndicator(modifier = Modifier.fillMaxWidth()) }
                }
                loadState.refresh is LoadState.Error -> {
                    val e = pexelPhotos.loadState.refresh as LoadState.Error
                    item { Text(text = e.error.localizedMessage ?: "Unknown Error") }
                }
                loadState.append is LoadState.Error -> {
                    val e = pexelPhotos.loadState.append as LoadState.Error
                    item { Text(text = e.error.localizedMessage ?: "Unknown Error") }
                }
            }
        }
    }
}

@Composable
fun PexelPhotoItem(photo: PexelEntity) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        AsyncImage(
            model = photo.photos[0].src.original,
            contentDescription = "photo.alt",
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = photo.photos[0].photographer, style = MaterialTheme.typography.displaySmall)
        Text(text = photo.photos[0].photographer, style = MaterialTheme.typography.displayLarge)
    }
}
