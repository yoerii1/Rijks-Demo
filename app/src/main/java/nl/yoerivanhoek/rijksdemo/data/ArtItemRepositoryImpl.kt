package nl.yoerivanhoek.rijksdemo.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import nl.yoerivanhoek.rijksdemo.domain.ArtItemRepository
import nl.yoerivanhoek.rijksdemo.domain.model.ArtItem

class ArtItemRepositoryImpl(
    private val collectionPagingSource: CollectionPagingSource,
    private val collectionRemoteDataSource: CollectionRemoteDataSource
) : ArtItemRepository {

    private companion object {
        const val NETWORK_PAGE_SIZE = 10
    }

    override fun getCollection(): Flow<PagingData<ArtItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                initialLoadSize = NETWORK_PAGE_SIZE
            ),
            pagingSourceFactory = { collectionPagingSource }
        ).flow
    }

    override suspend fun getArtDetails(artId: String) = Result.runCatching {
        collectionRemoteDataSource.getArtDetails(artId = artId)
    }
}

