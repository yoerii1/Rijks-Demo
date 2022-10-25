package nl.yoerivanhoek.rijksdemo.ui.generic

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import nl.yoerivanhoek.rijksdemo.R

@Composable
fun ErrorButton(onClickRetry: () -> Unit) {
    Button(
        modifier = Modifier.padding(all = 8.dp),
        onClick = onClickRetry
    ) {
        Text(
            text = stringResource(id = R.string.global_retry_message),
            textAlign = TextAlign.Center
        )
    }
}