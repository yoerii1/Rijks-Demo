package nl.yoerivanhoek.rijksdemo

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import nl.yoerivanhoek.rijksdemo.data.api.CollectionApiService
import nl.yoerivanhoek.rijksdemo.ui.detail.ArtDetailsScreen
import nl.yoerivanhoek.rijksdemo.ui.list.ArtOverviewScreen
import nl.yoerivanhoek.rijksdemo.ui.theme.RijksDemoTheme
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module


@RunWith(AndroidJUnit4::class)
class ArtDetailsTest {

    private companion object {
        val dummyCollectionResponse = ArtDummyDataFactory.getCollectionResponse()
        val dummyDetailsResponse = ArtDummyDataFactory.getDetailsResponse()
    }

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        loadKoinModules(module {
            factory<CollectionApiService> {
                object : CollectionApiService {
                    override suspend fun getCollection(page: Int, loadSize: Int) =
                        dummyCollectionResponse

                    override suspend fun getArtDetails(artId: String) = dummyDetailsResponse
                }
            }
        })
    }

    @Test
    fun testAllItemsAreDisplayed() {
        composeTestRule.setContent {
            RijksDemoTheme {
                ArtDetailsScreen(
                    dummyDetailsResponse.artObject.id,
                    navController = rememberNavController()
                )
            }
        }
        Thread.sleep(10_000)
        dummyDetailsResponse.artObject.let {
            composeTestRule.onNodeWithText(it.title).assertIsDisplayed()
            composeTestRule.onNodeWithText(it.principalOrFirstMaker).assertIsDisplayed()
            it.description?.let { description ->
                composeTestRule.onNodeWithText(description).assertIsDisplayed()
            }
            composeTestRule.onNodeWithText(it.objectNumber,).assertIsDisplayed()
            it.objectTypes.forEach { type ->
                composeTestRule.onNodeWithText(type, ignoreCase = true).assertIsDisplayed()
            }
        }
    }

}