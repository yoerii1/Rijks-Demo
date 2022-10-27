package nl.yoerivanhoek.rijksdemo.ui.generic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.yoerivanhoek.rijksdemo.ui.theme.Purple500
import nl.yoerivanhoek.rijksdemo.ui.theme.RijksDemoTheme

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
    Card(
        backgroundColor = backgroundColor
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            text = name.replaceFirstChar { it.uppercase() },
        )
    }
}

@Preview
@Composable
private fun Preview() {
    RijksDemoTheme {
        Chips(titles = listOf("Schilderij", "Model", "Object"))
    }
}