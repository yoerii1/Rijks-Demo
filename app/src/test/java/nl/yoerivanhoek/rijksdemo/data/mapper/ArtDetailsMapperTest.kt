package nl.yoerivanhoek.rijksdemo.data.mapper

import nl.yoerivanhoek.rijksdemo.data.model.ArtDetailResponse
import nl.yoerivanhoek.rijksdemo.data.model.ArtPieceDetailResponse
import nl.yoerivanhoek.rijksdemo.data.model.ImageResponse
import nl.yoerivanhoek.rijksdemo.domain.model.ArtDetails
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ArtDetailsMapperTest {

    private val artDetailsMapper = ArtDetailsMapper

    @Test
    fun `When mapping an artDetails, Then return listItems with the correct values`() {
        // Given
        val artItemResponse = ArtDetailResponse(
            artObject = ArtPieceDetailResponse(
                id = "id",
                objectNumber = "objectNumber",
                title = "title",
                description = "description",
                objectTypes = listOf("objectTypes"),
                principalOrFirstMaker = "principalOrFirstMaker",
                webImage = ImageResponse(
                    guid = "",
                    offsetPercentageX = 0,
                    offsetPercentageY = 0,
                    width = 0,
                    height = 0,
                    url = "url",
                ),
            )
        )

        // When
        val actual = artDetailsMapper.map(artItemResponse)

        // Then
        val expected = ArtDetails(
            objectNumber = artItemResponse.artObject.objectNumber,
            title = artItemResponse.artObject.title,
            description = artItemResponse.artObject.description,
            types = artItemResponse.artObject.objectTypes,
            author = artItemResponse.artObject.principalOrFirstMaker,
            imageUrl = artItemResponse.artObject.webImage.url,
        )
        assertEquals(expected, actual)
    }

}