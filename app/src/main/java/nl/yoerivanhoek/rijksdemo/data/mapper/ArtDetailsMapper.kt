package nl.yoerivanhoek.rijksdemo.data.mapper

import nl.yoerivanhoek.rijksdemo.data.model.ArtDetailResponse
import nl.yoerivanhoek.rijksdemo.domain.model.ArtDetails

object ArtDetailsMapper {

    fun map(data: ArtDetailResponse): ArtDetails = data.artObject.run {
        ArtDetails(
            objectNumber = objectNumber,
            title = title,
            description = description,
            types = objectTypes,
            author = principalOrFirstMaker,
            imageUrl = webImage.url,
        )
    }
}