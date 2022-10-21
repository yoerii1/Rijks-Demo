package nl.yoerivanhoek.rijksdemo.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtCollectionResponse (
	@SerialName("count")
	val count : Int,
	@SerialName("artObjects")
	val artObjects : List<ArtResponse>
)