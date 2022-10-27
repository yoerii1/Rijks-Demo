package nl.yoerivanhoek.rijksdemo.ui.detail

import androidx.annotation.StringRes
import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import nl.yoerivanhoek.rijksdemo.R
import nl.yoerivanhoek.rijksdemo.ui.util.TestTags.TAG_ART_DETAILS
import nl.yoerivanhoek.rijksdemo.ui.generic.Chips
import nl.yoerivanhoek.rijksdemo.ui.detail.ArtDetailsViewModel.ArtDetailsState.*
import nl.yoerivanhoek.rijksdemo.ui.generic.ErrorView
import nl.yoerivanhoek.rijksdemo.ui.generic.LoadingView
import nl.yoerivanhoek.rijksdemo.ui.theme.RijksDemoTheme
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ArtDetailsScreen(
    artId: String,
    artDetailsViewModel: ArtDetailsViewModel = getViewModel(parameters = { parametersOf(artId) }),
    navController: NavHostController
) {
    val artDetailsState by artDetailsViewModel.artDetailsState.observeAsState()

    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .testTag(TAG_ART_DETAILS)
    ) {
        when (val state = artDetailsState) {
            is Details -> ArtDetail(state) {
                navController.popBackStack()
            }
            Error -> ErrorView(
                modifier = Modifier.align(Alignment.Center),
                message = stringResource(id = R.string.global_error_message)
            ) {
                artDetailsViewModel.onRetry()
            }
            Loading -> LoadingView(modifier = Modifier.fillMaxSize())
            else -> Unit
        }
    }
}

@Composable
private fun ArtDetail(details: Details, onBackClicked: () -> Unit) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        Box {
            HeaderImage(details)
            BackButton(onBackClicked)
        }
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ArtTitle(text = details.title)
            Chips(titles = details.types)
            ArtProperty(label = R.string.description, value = details.description ?: "-")
            ArtProperty(label = R.string.author, value = details.author)
            ArtProperty(label = R.string.object_name, value = details.objectNumber)
        }
    }
}

@Composable
private fun HeaderImage(details: Details) {
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
}

@Composable
private fun BackButton(onBackClicked: () -> Unit) {
    Button(
        onClick = onBackClicked,
        modifier = Modifier.padding(top = 16.dp, start = 16.dp)
    ) {
        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
    }
}

@Composable
private fun ArtTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.h5,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.onSurface
    )
}

@Composable
private fun ArtProperty(@StringRes label: Int, value: String) {
    Divider(modifier = Modifier.padding(bottom = 4.dp))
    Column {
        Text(
            text = stringResource(id = label),
            modifier = Modifier.height(24.dp),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSurface
        )
        Text(
            text = value,
            style = MaterialTheme.typography.body1,
            overflow = TextOverflow.Visible,
            color = MaterialTheme.colors.onSurface
        )
    }
}

@Composable
@Preview
private fun ArtDetailPreview() {
    RijksDemoTheme {
        ArtDetail(
            details = Details(
                "objectNumber",
                "title",
                "description",
                listOf("types"),
                "author",
                "imageUrl",
            )
        ) {}
    }
}