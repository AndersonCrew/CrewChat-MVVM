package com.crewcloud.apps.crewchat.presentation.core.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.crewcloud.apps.crewchat.R

/**
 * Created by BM Anderson on 8/7/26.
 */
@Composable
fun DazoneImage(
    imageUrl: String?,
    contentScale: ContentScale = ContentScale.Crop,
    modifier: Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current).data(imageUrl).crossfade(false)
            .memoryCachePolicy(
                CachePolicy.ENABLED
            ).diskCachePolicy(CachePolicy.ENABLED).build(),
        contentDescription = null,
        contentScale = contentScale,
        placeholder = painterResource(R.drawable.avatar_default),
        error = painterResource(R.drawable.avatar_default),
        modifier = modifier
    )
}