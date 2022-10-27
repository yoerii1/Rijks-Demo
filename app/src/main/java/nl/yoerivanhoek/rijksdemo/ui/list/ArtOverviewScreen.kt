package nl.yoerivanhoek.rijksdemo.ui.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import nl.yoerivanhoek.rijksdemo.R
import nl.yoerivanhoek.rijksdemo.ui.generic.ErrorButton
import nl.yoerivanhoek.rijksdemo.ui.generic.ErrorView
import nl.yoerivanhoek.rijksdemo.ui.generic.LoadingView
import nl.yoerivanhoek.rijksdemo.ui.model.ArtUiModel
import nl.yoerivanhoek.rijksdemo.ui.model.ArtUiModel.ArtItem
import nl.yoerivanhoek.rijksdemo.ui.model.ArtUiModel.AuthorSeparator
import nl.yoerivanhoek.rijksdemo.ui.theme.RijksDemoTheme
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
            .background(MaterialTheme.colors.background),
        state = artItems.rememberLazyListState()
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
        item {
            ListLoadingItem(artItems)
        }
        item {
            ListErrorItem(artItems)
        }
    }
}

@Composable
private fun AuthorHeader(author: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Text(
            modifier = Modifier.padding(all = 16.dp),
            text = author,
            style = TextStyle(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp
            ),
        )
    }
}

@Composable
private fun LazyItemScope.ListLoadingItem(lazyArtCollection: LazyPagingItems<ArtUiModel>) {
    val loadState = lazyArtCollection.loadState
    when {
        loadState.refresh is LoadState.Loading -> {
            LoadingView(modifier = Modifier.fillParentMaxSize())
        }
        loadState.append is LoadState.Loading -> {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
private fun LazyItemScope.ListErrorItem(lazyArtCollection: LazyPagingItems<ArtUiModel>) {
    val loadState = lazyArtCollection.loadState
    when {
        loadState.refresh is LoadState.Error -> {
            ErrorView(
                message = stringResource(id = R.string.global_error_message),
                modifier = Modifier.fillParentMaxSize()
            ) { lazyArtCollection.retry() }
        }
        loadState.append is LoadState.Error -> {
            Box(modifier = Modifier.fillMaxWidth()) {
                ErrorButton(modifier = Modifier.align(Alignment.Center)) { lazyArtCollection.retry() }
            }
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
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}

// After recreation, LazyPagingItems first return 0 items, then the cached items.
// This behavior/issue is resetting the LazyListState scroll position.
// Below is a workaround. More info: https://issuetracker.google.com/issues/177245496.
@Composable
fun <T : Any> LazyPagingItems<T>.rememberLazyListState(): LazyListState {
    return when (itemCount) {
        // Return a different LazyListState instance.
        0 -> remember(this) { LazyListState(0, 0) }
        // Return rememberLazyListState (normal case).
        else -> androidx.compose.foundation.lazy.rememberLazyListState()
    }
}

@Composable
@Preview
private fun AuthorHeaderPreview() {
    RijksDemoTheme {
        AuthorHeader(author = "Author")
    }
}

@Composable
@Preview
private fun ArtItemPreview() {
    RijksDemoTheme {
        ArtItem(artItem = ArtItem(
            "id",
            "title",
            "author",
            "imagUrl"
        ))
    }
}