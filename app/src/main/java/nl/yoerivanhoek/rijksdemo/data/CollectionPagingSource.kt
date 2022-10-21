package nl.yoerivanhoek.rijksdemo.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import nl.yoerivanhoek.rijksdemo.domain.model.ArtItem

//TODO:
// Collection source == domain logic?
// Let repo map to domain instead of datasource
// Lifecycle for collecting flow in background?

class CollectionPagingSource(
    private val collectionRemoteDataSource: CollectionRemoteDataSource
) : PagingSource<Int, ArtItem>() {

    private companion object {
        const val RIJKS_STARTING_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArtItem> {
        val position = params.key ?: RIJKS_STARTING_PAGE_INDEX
        return try {
            val collection = collectionRemoteDataSource.getArtCollection(position, params.loadSize)

            LoadResult.Page(
                data = collection,
                prevKey = if (position == RIJKS_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArtItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}