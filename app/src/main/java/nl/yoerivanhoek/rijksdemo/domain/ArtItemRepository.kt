package nl.yoerivanhoek.rijksdemo.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import nl.yoerivanhoek.rijksdemo.domain.model.ArtItem
import nl.yoerivanhoek.rijksdemo.domain.model.ArtDetails

interface ArtItemRepository {

    fun getCollection(): Flow<PagingData<ArtItem>>

    suspend fun getArtDetails(artId: String): Result<ArtDetails>

}