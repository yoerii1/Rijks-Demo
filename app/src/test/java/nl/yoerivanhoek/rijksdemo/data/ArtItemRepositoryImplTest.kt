package nl.yoerivanhoek.rijksdemo.data

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import nl.yoerivanhoek.rijksdemo.domain.model.ArtDetails
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class ArtItemRepositoryImplTest {
    private val collectionPagingSource: CollectionPagingSource = mockk()
    private val collectionRemoteDataSource: CollectionRemoteDataSource = mockk()

    private val collectionRepositoryImpl = ArtItemRepositoryImpl(
        collectionPagingSource, collectionRemoteDataSource
    )

    @Test
    fun `Given remoteDataSource throws exception, When getting artDetails, Then return failure result`() = runTest {
        // Given
        val expectedException = Exception()
        coEvery { collectionRemoteDataSource.getArtDetails(any()) } throws expectedException

        // When
        val result = collectionRepositoryImpl.getArtDetails("")

        // Then
        assertEquals(expectedException, result.exceptionOrNull())
    }

    @Test
    fun `When getting artDetails, Then return details from remote datasource`() = runTest {
        // Given
        val expectedDetails: ArtDetails = mockk()
        coEvery { collectionRemoteDataSource.getArtDetails(any()) } returns expectedDetails

        // When
        val result = collectionRepositoryImpl.getArtDetails("")

        // Then
        assertEquals(expectedDetails, result.getOrNull())
    }
}