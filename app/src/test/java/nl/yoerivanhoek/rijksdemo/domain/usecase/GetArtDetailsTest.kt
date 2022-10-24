package nl.yoerivanhoek.rijksdemo.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import nl.yoerivanhoek.rijksdemo.domain.ArtItemRepository
import nl.yoerivanhoek.rijksdemo.domain.model.ArtDetails
import nl.yoerivanhoek.rijksdemo.domain.model.ArtItem
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class GetArtDetailsTest {

    @Test
    fun `Given repo returns failure, When getting artDetails, Then return failure`() = runTest {
        // Given
        val getArtDetails = GetArtDetails(repo(null))

        // When
        val result = getArtDetails("")

        // Then
        assertTrue(result.isFailure)
    }

    @Test
    fun `Given repo returns artDetails, When getting artDetails, Then return those details`() = runTest {
        // Given
        val expected = ArtDetails(
            objectNumber = "objectNumber",
            title = "title",
            description = "description",
            types = listOf("types"),
            author = "author",
            imageUrl = "imageUrl",
        )
        val getArtDetails = GetArtDetails(repo(artDetailsToReturn = expected))

        // When
        val result = getArtDetails("")

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expected, result.getOrNull())
    }

    private fun repo(artDetailsToReturn: ArtDetails? = null) = object : ArtItemRepository {
        override fun getCollection(): Flow<PagingData<ArtItem>> = flowOf()

        override suspend fun getArtDetails(artId: String) = artDetailsToReturn?.let {
            Result.success(it)
        } ?: Result.failure(Exception())

    }

}