package nl.yoerivanhoek.rijksdemo

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import nl.yoerivanhoek.rijksdemo.data.api.CollectionApi
import nl.yoerivanhoek.rijksdemo.ui.util.TestTags
import nl.yoerivanhoek.rijksdemo.ui.list.ArtOverviewScreen
import nl.yoerivanhoek.rijksdemo.ui.theme.RijksDemoTheme
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class ArtOverviewTest {

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
            factory<CollectionApi> {
                object : CollectionApi {
                    override suspend fun getCollection(page: Int, loadSize: Int) = dummyCollectionResponse
                    override suspend fun getArtDetails(artId: String) = dummyDetailsResponse
                }
            }
        })
    }

    @Test
    fun testAllItemsAreDisplayed() {
        composeTestRule.setContent {
            RijksDemoTheme {
                ArtOverviewScreen()
            }
        }
        dummyCollectionResponse.artObjects.forEach {
            composeTestRule.onNodeWithText(it.title).assertIsDisplayed()
            composeTestRule.onNodeWithText(it.principalOrFirstMaker).assertIsDisplayed()
        }
    }

    @Test
    fun testClickItemNavigatesToDetail() {
        composeTestRule.setContent {
            RijksDemoTheme {
                ArtOverviewScreen()
            }
        }
        dummyCollectionResponse.artObjects.first().let {
            composeTestRule.onNodeWithText(it.title).performClick()
        }
        composeTestRule.onNodeWithTag(TestTags.TAG_ART_DETAILS)
    }
}