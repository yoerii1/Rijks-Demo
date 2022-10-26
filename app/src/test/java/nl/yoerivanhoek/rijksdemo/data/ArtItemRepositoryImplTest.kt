package nl.yoerivanhoek.rijksdemo.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import nl.yoerivanhoek.rijksdemo.domain.model.ArtDetails
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
internal class ArtItemRepositoryImplTest {
    private val collectionPagingSource: CollectionPagingSource = mock()
    private val collectionRemoteDataSource: CollectionRemoteDataSource = mock()

    private val collectionRepositoryImpl = ArtItemRepositoryImpl(
        collectionPagingSource, collectionRemoteDataSource
    )

    @Test
    fun `Given remoteDataSource throws exception, When getting artDetails, Then return failure result`() = runTest {
        // Given
        val expectedException = Exception()
        whenever(collectionRemoteDataSource.getArtDetails(any())).then { throw expectedException }

        // When
        val result = collectionRepositoryImpl.getArtDetails("")

        // Then
        assertEquals(expectedException, result.exceptionOrNull())
    }

    @Test
    fun `When getting artDetails, Then return details from remote datasource`() = runTest {
        // Given
        val expectedDetails: ArtDetails = mock()
        whenever(collectionRemoteDataSource.getArtDetails(any())).thenReturn(expectedDetails)

        // When
        val result = collectionRepositoryImpl.getArtDetails("")

        // Then
        assertEquals(expectedDetails, result.getOrNull())
    }
}