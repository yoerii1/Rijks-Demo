package nl.yoerivanhoek.rijksdemo.ui.list

import nl.yoerivanhoek.rijksdemo.domain.model.ArtItem
import nl.yoerivanhoek.rijksdemo.ui.model.ArtUiModel

object ArtUiModelMapper {

    fun map(artItem: ArtItem) = with(artItem) { ArtUiModel.ArtItem(objectNumber, title, author, imageUrl) }
}