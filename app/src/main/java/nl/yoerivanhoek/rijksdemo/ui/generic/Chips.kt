package nl.yoerivanhoek.rijksdemo.ui.generic

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
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        titles.forEach {
            Chip(name = it)
        }
    }
}

@Composable
fun Chip(
    name: String,
    backgroundColor: Color = Purple500
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = backgroundColor
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            text = name.capitalize()
        )
    }
}

@Preview
@Composable
private fun Preview() {
    Chips(titles = listOf("Schilderij", "Model", "Object"))
}