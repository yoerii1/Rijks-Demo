package nl.yoerivanhoek.rijksdemo.domain.usecase

import nl.yoerivanhoek.rijksdemo.domain.ArtItemRepository

class GetArtDetails(
    private val collectionRepository: ArtItemRepository
) {

    suspend operator fun invoke(artId: String) = collectionRepository.getArtDetails(artId = artId)

}