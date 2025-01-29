package nl.yoerivanhoek.rijksdemo

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.yoerivanhoek.rijksdemo.ui.detail.ArtDetailsScreen
import nl.yoerivanhoek.rijksdemo.ui.list.ArtOverviewScreen
import nl.yoerivanhoek.rijksdemo.ui.navigation.ArtDetailsArgs
import nl.yoerivanhoek.rijksdemo.ui.navigation.ArtListArgs
import nl.yoerivanhoek.rijksdemo.ui.theme.RijksDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RijksDemoTheme {
                RijksDemoNavHost(
                    modifier = Modifier
                        .displayCutoutPadding()
                )
            }
        }
    }
}

@Composable
fun RijksDemoNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: Parcelable = ArtListArgs
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable<ArtListArgs> {
            ArtOverviewScreen(onArtItemClick = { artId ->
                val args = ArtDetailsArgs(artId)
                navController.navigate(args)
            })
        }
        composable<ArtDetailsArgs> { backStackEntry ->
            val artDetailsArgs: ArtDetailsArgs = backStackEntry.toRoute()
            ArtDetailsScreen(artDetailsArgs.artId, navController = navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RijksDemoTheme {
        ArtOverviewScreen()
    }
}