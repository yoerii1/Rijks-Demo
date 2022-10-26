package nl.yoerivanhoek.rijksdemo.domain.usecase

import kotlinx.coroutines.test.runTest
import nl.yoerivanhoek.rijksdemo.domain.ArtItemRepository
import nl.yoerivanhoek.rijksdemo.domain.model.ArtDetails
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class GetArtDetailsTest {

    private val repository: ArtItemRepository = mock()

    @Test
    fun `Given repo returns failure, When getting artDetails, Then return failure`() = runTest {
        // Given
        whenever(repository.getArtDetails(any())).thenReturn(Result.failure(Exception()))

        val getArtDetails = GetArtDetails(repository)

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
        whenever(repository.getArtDetails(any())).thenReturn(Result.success(expected))

        val getArtDetails = GetArtDetails(repository)

        // When
        val result = getArtDetails("")

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expected, result.getOrNull())
    }

}