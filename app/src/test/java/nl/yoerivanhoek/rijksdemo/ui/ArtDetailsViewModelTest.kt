package nl.yoerivanhoek.rijksdemo.ui

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import nl.yoerivanhoek.rijksdemo.InstantExecutorExtension
import nl.yoerivanhoek.rijksdemo.TestCoroutineExtension
import nl.yoerivanhoek.rijksdemo.domain.ArtItemRepository
import nl.yoerivanhoek.rijksdemo.domain.model.ArtDetails
import nl.yoerivanhoek.rijksdemo.domain.model.ArtItem
import nl.yoerivanhoek.rijksdemo.domain.usecase.GetArtDetails
import nl.yoerivanhoek.rijksdemo.ui.ArtDetailsViewModel.ArtDetailsState.Details
import nl.yoerivanhoek.rijksdemo.ui.ArtDetailsViewModel.ArtDetailsState.Error
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension

@ExtendWith(InstantExecutorExtension::class)
internal class ArtDetailsViewModelTest {

    @JvmField
    @RegisterExtension
    val coroutines = TestCoroutineExtension()

    @Test
    fun `Given artDetails cannot be loaded, When initializing the VM, Then error state is set`() {
        // Given
        val getArtDetails = getArtDetailsFake(null)

        // When
        val artDetailsViewModel = ArtDetailsViewModel(
            "id",
            getArtDetails
        )

        // Then
        assertTrue(artDetailsViewModel.artDetailsState.value is Error)
    }

    @Test
    fun `Given artDetails are loaded successfully, When initializing the VM, Then it's reflected in the screen state`() {
        // Given
        val expected = ArtDetails(
            objectNumber = "objectNumber",
            title = "title",
            description = "description",
            types = listOf("types"),
            author = "author",
            imageUrl = "imageUrl",
        )
        val getArtDetails = getArtDetailsFake(expected)

        // When
        val artDetailsViewModel = ArtDetailsViewModel(
            "id",
            getArtDetails
        )

        // Then
        val actual = artDetailsViewModel.artDetailsState.value as? Details
        assertEquals(expected.objectNumber, actual?.objectNumber)
        assertEquals(expected.title, actual?.title)
        assertEquals(expected.description, actual?.description)
        assertEquals(expected.types, actual?.types)
        assertEquals(expected.author, actual?.author)
        assertEquals(expected.imageUrl, actual?.imageUrl)
    }

    private fun getArtDetailsFake(artDetailsToReturn: ArtDetails?): GetArtDetails {
        val artItemsRepository = object : ArtItemRepository {
            override fun getCollection(): Flow<PagingData<ArtItem>> = flowOf()

            override suspend fun getArtDetails(artId: String) = artDetailsToReturn?.let {
                Result.success(it)
            } ?: Result.failure(Exception())
        }

        return GetArtDetails(artItemsRepository)
    }
}