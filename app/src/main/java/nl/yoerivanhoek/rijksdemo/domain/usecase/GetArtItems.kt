package nl.yoerivanhoek.rijksdemo.domain.usecase

import nl.yoerivanhoek.rijksdemo.domain.ArtItemRepository

class GetArtItems(
    private val artItemRepository: ArtItemRepository
) {

    operator fun invoke() = artItemRepository.getCollection()

}