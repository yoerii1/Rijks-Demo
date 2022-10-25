package nl.yoerivanhoek.rijksdemo.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import nl.yoerivanhoek.rijksdemo.R
import nl.yoerivanhoek.rijksdemo.TestTags.TAG_ART_DETAILS
import nl.yoerivanhoek.rijksdemo.ui.generic.Chips
import nl.yoerivanhoek.rijksdemo.ui.list.ErrorView
import nl.yoerivanhoek.rijksdemo.ui.list.LoadingView
import nl.yoerivanhoek.rijksdemo.ui.detail.ArtDetailsViewModel.ArtDetailsState.*
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ArtDetailsScreen(
    artId: String,
    artDetailsViewModel: ArtDetailsViewModel = getViewModel(parameters = { parametersOf(artId) }),
    navController: NavHostController
) {
    val artDetailsState by artDetailsViewModel.artDetailsState.observeAsState()

    Box(modifier = Modifier
        .fillMaxSize()
        .testTag(TAG_ART_DETAILS)) {
        when (val state = artDetailsState) {
            is Details -> ArtDetail(state) {
                navController.popBackStack()
            }
            Error -> ErrorView(message = stringResource(id = R.string.global_error_message)) {
                artDetailsViewModel.onRetry()
            }
            Loading -> Box(modifier = Modifier.fillMaxSize()) {
                LoadingView(modifier = Modifier.align(Alignment.Center))
            }
            else -> Unit
        }
    }
}

@Composable
fun ArtDetail(details: Details, onBackClicked: () -> Unit) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints {
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                ) {
                    Box {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxSize(),
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(details.imageUrl)
                                .crossfade(300)
                                .build(),
                            contentDescription = details.title,
                            contentScale = ContentScale.Crop
                        )
                        Button(onClick = onBackClicked, modifier = Modifier.padding(all = 16.dp)) {
                            Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
                        }
                    }
                    Column {
                        ArtTitle(text = details.title)
                        Chips(titles = details.types, startSpacing = 16.dp, endSpacing = 16.dp)
                        ArtProperty(
                            stringResource(
                                id = R.string.description
                            ), details.description ?: "-"
                        )
                        ArtProperty(
                            stringResource(
                                id = R.string.author
                            ), details.author
                        )
                        ArtProperty(
                            stringResource(
                                id = R.string.object_name
                            ), details.objectNumber
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ArtTitle(
    text: String
) {
    Column(modifier = Modifier.padding(all = 16.dp)) {
        Text(
            text = text,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ArtProperty(label: String, value: String) {
    Column(modifier = Modifier.padding(all = 16.dp)) {
        Divider(modifier = Modifier.padding(bottom = 4.dp))
        Text(
            text = label,
            modifier = Modifier.height(24.dp),
            style = MaterialTheme.typography.caption,
        )
        Text(
            text = value,
            modifier = Modifier.height(24.dp),
            style = MaterialTheme.typography.body1,
            overflow = TextOverflow.Visible
        )
    }
}