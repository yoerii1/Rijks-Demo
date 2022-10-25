package nl.yoerivanhoek.rijksdemo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import nl.yoerivanhoek.rijksdemo.ui.theme.Purple500

@Composable
fun Chips(
    modifier: Modifier = Modifier,
    titles: List<String>,
    startSpacing: Dp = 0.dp,
    endSpacing: Dp = 0.dp
) {
    LazyRow(modifier = modifier) {
        if (startSpacing != 0.dp) {
            item {
                Spacer(Modifier.width(startSpacing))
            }
        }

        items(titles) {
            Chip(
                name = it,
            )
        }

        if (endSpacing != 0.dp) {
            item {
                Spacer(Modifier.width(endSpacing))
            }
        }
    }
}

@Composable
fun Chip(
    name: String,
    backgroundColor: Color = Purple500
) {
    Surface(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .height(32.dp),
        shape = MaterialTheme.shapes.medium,
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = name.capitalize())
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Chips(titles = listOf("Schilderij", "Model", "Object"))
}