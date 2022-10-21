package nl.yoerivanhoek.rijksdemo.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import nl.yoerivanhoek.rijksdemo.domain.model.ArtItem
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class CollectionPagingSourceTest {

    private val collectionRemoteDataSource: CollectionRemoteDataSource = mockk()

    private val collectionPagingSource = CollectionPagingSource(
        collectionRemoteDataSource
    )

    @Test
    fun `Given remoteDatasource fails, When loading, Then return error`() = runTest {
        // Given
        val expectedException = Exception()
        coEvery { collectionRemoteDataSource.getArtCollection(1, 1) } throws expectedException

        // When
        val actual = collectionPagingSource.load(PagingSource.LoadParams.Append(1, 1, false))

        assertEquals(expectedException, (actual as PagingSource.LoadResult.Error).throwable)
    }

    @Test
    fun `Given LoadParams key is null, When loading, Then return prevKey as null`() = runTest {
        // Given
        coEvery { collectionRemoteDataSource.getArtCollection(1, 1) } returns listOf()

        // When
        val actual = collectionPagingSource.load(PagingSource.LoadParams.Refresh(null, 1, false))

        assertNull((actual as PagingSource.LoadResult.Page).prevKey)
    }

    @Test
    fun `Given LoadParams key is passed, When loading, Then return prevKey as 1 less than passed key`() = runTest {
        // Given
        coEvery { collectionRemoteDataSource.getArtCollection(5, 1) } returns listOf()

        // When
        val actual = collectionPagingSource.load(PagingSource.LoadParams.Append(5, 1, false))

        assertEquals(4, (actual as PagingSource.LoadResult.Page).prevKey)
    }

    @Test
    fun `Given LoadParams key is passed, When loading, Then return nextKey as 1 more than passed key`() = runTest {
        // Given
        coEvery { collectionRemoteDataSource.getArtCollection(5, 1) } returns listOf()

        // When
        val actual = collectionPagingSource.load(PagingSource.LoadParams.Append(5, 1, false))

        assertEquals(6, (actual as PagingSource.LoadResult.Page).nextKey)
    }

    @Test
    fun `Given appending, When loading, Then return artItems from remote datasource`() = runTest {
        // Given
        val expectedArtItems: List<ArtItem> = mockk()
        coEvery { collectionRemoteDataSource.getArtCollection(5, 1) } returns expectedArtItems

        // When
        val result = collectionPagingSource.load(PagingSource.LoadParams.Append(5, 1, false))

        val actualArtItems = (result as PagingSource.LoadResult.Page).data
        assertEquals(expectedArtItems, actualArtItems)
    }

    @Test
    fun `Given refreshing, When loading, Then return artItems from remote datasource`() = runTest {
        // Given
        val expectedArtItems: List<ArtItem> = mockk()
        coEvery { collectionRemoteDataSource.getArtCollection(5, 1) } returns expectedArtItems

        // When
        val result = collectionPagingSource.load(PagingSource.LoadParams.Refresh(5, 1, false))

        val actualArtItems = (result as PagingSource.LoadResult.Page).data
        assertEquals(expectedArtItems, actualArtItems)
    }

    @Test
    fun `Given prepending, When loading, Then return artItems from remote datasource`() = runTest {
        // Given
        val expectedArtItems: List<ArtItem> = mockk()
        coEvery { collectionRemoteDataSource.getArtCollection(5, 1) } returns expectedArtItems

        // When
        val result = collectionPagingSource.load(PagingSource.LoadParams.Prepend(5, 1, false))

        val actualArtItems = (result as PagingSource.LoadResult.Page).data
        assertEquals(expectedArtItems, actualArtItems)
    }

    @Test
    fun `Given an anchor position has a prevKey, When getting the refresh key, Then return prevKey + 1`() {
        // Given
        val prevKey = 8
        val state: PagingState<Int, ArtItem> = mockk()
        every { state.anchorPosition } returns 1

        val page: PagingSource.LoadResult.Page<Int, ArtItem> = mockk()
        every { state.closestPageToPosition(1) } returns page
        every { page.prevKey } returns prevKey

        // When
        val actualKey = collectionPagingSource.getRefreshKey(state)

        val expectedKey = prevKey + 1
        assertEquals(expectedKey, actualKey)
    }

    @Test
    fun `Given an anchor position has a nextKey and no prevKey, When getting the refresh key, Then return nextKey - 1`() {
        // Given
        val nextKey = 8
        val state: PagingState<Int, ArtItem> = mockk()
        every { state.anchorPosition } returns 1

        val page: PagingSource.LoadResult.Page<Int, ArtItem> = mockk()
        every { state.closestPageToPosition(1) } returns page
        every { page.prevKey } returns null
        every { page.nextKey } returns nextKey

        // When
        val actualKey = collectionPagingSource.getRefreshKey(state)

        val expectedKey = nextKey - 1
        assertEquals(expectedKey, actualKey)
    }

    @Test
    fun `Given an anchor position has no nextKey and no prevKey, When getting the refresh key, Then return null`() {
        // Given
        val state: PagingState<Int, ArtItem> = mockk()
        every { state.anchorPosition } returns 1

        val page: PagingSource.LoadResult.Page<Int, ArtItem> = mockk()
        every { state.closestPageToPosition(1) } returns page
        every { page.prevKey } returns null
        every { page.nextKey } returns null

        // When
        val actualKey = collectionPagingSource.getRefreshKey(state)

        assertNull(actualKey)
    }

    @Test
    fun `Given no anchor position, When getting the refresh key, Then return null`() {
        // Given
        val state: PagingState<Int, ArtItem> = mockk()
        every { state.anchorPosition } returns null

        // When
        val actualKey = collectionPagingSource.getRefreshKey(state)

        assertNull(actualKey)
    }
}