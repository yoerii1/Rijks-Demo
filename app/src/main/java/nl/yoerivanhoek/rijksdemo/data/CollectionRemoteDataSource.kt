package nl.yoerivanhoek.rijksdemo.data

import nl.yoerivanhoek.rijksdemo.data.api.CollectionApiService
import nl.yoerivanhoek.rijksdemo.data.mapper.ArtCollectionMapper
import nl.yoerivanhoek.rijksdemo.data.mapper.ArtDetailsMapper
import nl.yoerivanhoek.rijksdemo.domain.model.ArtDetails
import nl.yoerivanhoek.rijksdemo.domain.model.ArtItem

class CollectionRemoteDataSource(
    private val collectionApiService: CollectionApiService,
    private val collectionMapper: ArtCollectionMapper,
    private val artDetailsMapper: ArtDetailsMapper
) {

    suspend fun getArtCollection(page: Int, loadSize: Int): List<ArtItem> {
        val response = collectionApiService.getCollection(page, loadSize)
        return collectionMapper.map(response)
    }

    suspend fun getArtDetails(artId: String): ArtDetails {
        val response = collectionApiService.getArtDetails(artId)
        return artDetailsMapper.map(response)
    }

}