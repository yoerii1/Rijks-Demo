package nl.yoerivanhoek.rijksdemo.data.mapper

import nl.yoerivanhoek.rijksdemo.data.model.ArtCollectionResponse
import nl.yoerivanhoek.rijksdemo.domain.model.ArtItem

object ArtCollectionMapper : Mapper<ArtCollectionResponse, List<ArtItem>> {

    override fun map(data: ArtCollectionResponse) = data.artObjects.map {
        ArtItem(
            objectNumber = it.objectNumber,
            title = it.title,
            author = it.principalOrFirstMaker,
            imageUrl = it.webImage.url
        )
    }
}

