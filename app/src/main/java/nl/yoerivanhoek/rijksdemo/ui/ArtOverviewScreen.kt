package nl.yoerivanhoek.rijksdemo.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import nl.yoerivanhoek.rijksdemo.R
import nl.yoerivanhoek.rijksdemo.ui.model.ArtUiModel
import nl.yoerivanhoek.rijksdemo.ui.model.ArtUiModel.ArtItem
import nl.yoerivanhoek.rijksdemo.ui.model.ArtUiModel.AuthorSeparator
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArtOverviewScreen(
    viewModel: ArtOverviewViewModel = getViewModel(),
    onArtItemClick: (String) -> Unit = {}
) {
    val artItems = viewModel.artCollectionFlow.collectAsLazyPagingItems()
    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        for (index in 0 until artItems.itemCount) {
            artItems.peek(index)?.let {
                when (it) {
                    is AuthorSeparator -> stickyHeader {
                        AuthorHeader(it.author)
                    }
                    is ArtItem -> item {
                        val artData = artItems[index] as ArtItem
                        ArtItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onArtItemClick(artData.id) },
                            artItem = artData
                        )
                    }
                }
            }
        }
        renderLoading(artItems)
        renderError(artItems)
    }
}

@Composable
private fun AuthorHeader(author: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Row(
            Modifier
                .padding(all = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(36.dp),
                painter = painterResource(id = R.drawable.ic_twotone_account_circle_24),
                contentDescription = null
            )
            Text(
                text = author,
                style = TextStyle(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp
                ),
            )
        }
    }
}

private fun LazyListScope.renderLoading(lazyArtCollection: LazyPagingItems<ArtUiModel>) {
    lazyArtCollection.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
            }
            loadState.append is LoadState.Loading -> {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }
            else -> return
        }
    }
}

private fun LazyListScope.renderError(lazyArtCollection: LazyPagingItems<ArtUiModel>) {
    lazyArtCollection.apply {
        when {
            loadState.refresh is LoadState.Error -> {
                val e = lazyArtCollection.loadState.refresh as LoadState.Error
                item {
                    ErrorView(
                        message = e.error.localizedMessage ?: "",
                        modifier = Modifier.fillParentMaxSize()
                    ) { retry() }
                }
            }
            loadState.append is LoadState.Error -> {
                item {
                    ErrorButton { retry() }
                }
            }
            else -> return
        }
    }
}

@Composable
fun ArtItem(modifier: Modifier = Modifier, artItem: ArtItem) {
    Card(
        modifier = modifier
            .padding(16.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(MaterialTheme.colors.surface)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.Center)
                )
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize(),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(artItem.imageUrl)
                        .crossfade(300)
                        .build(),
                    contentDescription = artItem.title,
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                artItem.title,
                modifier = Modifier.padding(16.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
fun LoadingView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    message: String,
    onClickRetry: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))
        ErrorButton(onClickRetry = onClickRetry)
    }
}

@Composable
fun ErrorButton(onClickRetry: () -> Unit) {
    Button(
        modifier = Modifier.padding(all = 8.dp),
        onClick = onClickRetry
    ) {
        Text(
            text = "Retry",
            textAlign = TextAlign.Center
        )
    }
}