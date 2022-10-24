package nl.yoerivanhoek.rijksdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nl.yoerivanhoek.rijksdemo.ui.ArtDetailsScreen
import nl.yoerivanhoek.rijksdemo.ui.ArtOverviewScreen
import nl.yoerivanhoek.rijksdemo.ui.theme.RijksDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RijksDemoTheme {
                RijksDemoNavHost()
            }
        }
    }
}

@Composable
fun RijksDemoNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "artlist"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("artlist") {
            ArtOverviewScreen(onArtItemClick = { artId ->
                navController.navigate("artdetails/$artId")
            })
        }
        composable("artdetails/{artId}") { backStackEntry ->
            backStackEntry.arguments?.getString("artId")?.let { artId ->
                ArtDetailsScreen(artId, navController = navController)
            }
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