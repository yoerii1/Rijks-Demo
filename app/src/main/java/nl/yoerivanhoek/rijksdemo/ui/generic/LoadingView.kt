package nl.yoerivanhoek.rijksdemo.ui.generic

import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import nl.yoerivanhoek.rijksdemo.ui.theme.RijksDemoTheme

@Composable
fun LoadingView(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
    ) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}


@Composable
@Preview
private fun Preview() {
    RijksDemoTheme {
        LoadingView()
    }
}