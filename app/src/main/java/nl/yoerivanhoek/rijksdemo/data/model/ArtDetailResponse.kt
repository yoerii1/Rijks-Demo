package nl.yoerivanhoek.rijksdemo.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtDetailResponse (
	@SerialName("artObject")
	val artObject : ArtPieceDetailResponse,
)