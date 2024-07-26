package com.example.paginationpexel.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import coil.request.ImageRequest
import com.example.paginationpexel.local.PexelEntity

//@Composable
//fun HomeScreen(
//    modifier: Modifier = Modifier,
//    images: LazyPagingItems<PexelEntity>,
//) {
//    LazyVerticalStaggeredGrid(
//        modifier = modifier,
//        columns = StaggeredGridCells.Adaptive(120.dp),
//        contentPadding = PaddingValues(10.dp),
//        verticalItemSpacing = 10.dp,
//        horizontalArrangement = Arrangement.spacedBy(10.dp)
//    ) {
//        items(count = images.itemCount) { index ->
//            val image = images[index]
//            ImageCard(
//                image = image,
//                modifier = Modifier
//                    .clickable { image?.id?.let { onImageClick(it) } }
//                    .pointerInput(Unit) {
//                        detectDragGesturesAfterLongPress(
//                            onDragStart = { onImageDragStart(image) },
//                            onDragCancel = { onImageDragEnd() },
//                            onDragEnd = { onImageDragEnd() },
//                            onDrag = { _, _ -> }
//                        )
//                    },
//                onToggleFavoriteStatus = { image?.let { onToggleFavoriteStatus(it) } },
//                isFavorite = favoriteImageIds.contains(image?.id)
//            )
//        }
//    }
//}
//
//@Composable
//
//@Composable
//fun ImageCard(
//    modifier: Modifier = Modifier,
//    image: PexelEntity?
//) {
//    val imageRequest = ImageRequest.Builder(LocalContext.current)
//        .data(image?.)
//        .crossfade(true)
//        .build()
//    val aspectRatio: Float by remember {
//        derivedStateOf { (image?.width?.toFloat() ?: 1f) / (image?.height?.toFloat() ?: 1f) }
//    }
//
//    Card(
//        shape = RoundedCornerShape(10.dp),
//        modifier = Modifier
//            .fillMaxWidth()
//            .aspectRatio(aspectRatio)
//            .then(modifier)
//    ) {
//        Box {
//            AsyncImage(
//                model = imageRequest,
//                contentDescription = null,
//                contentScale = ContentScale.FillBounds,
//                modifier = Modifier.fillMaxSize()
//            )
//            FavoriteButton(
//                isFavorite = isFavorite,
//                onClick = onToggleFavoriteStatus,
//                modifier = Modifier.align(Alignment.BottomEnd)
//            )
//        }
//    }
//}
//
//}
