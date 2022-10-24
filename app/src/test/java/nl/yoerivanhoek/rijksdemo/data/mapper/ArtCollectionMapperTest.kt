package nl.yoerivanhoek.rijksdemo.data.mapper

import nl.yoerivanhoek.rijksdemo.data.model.ArtCollectionResponse
import nl.yoerivanhoek.rijksdemo.data.model.ArtResponse
import nl.yoerivanhoek.rijksdemo.data.model.ImageResponse
import nl.yoerivanhoek.rijksdemo.domain.model.ArtItem
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ArtCollectionMapperTest {

    private val artCollectionMapper = ArtCollectionMapper

    @Test
    fun `When mapping an artCollection, Then return listItems with the correct values`() {
        // Given
        val response = ArtCollectionResponse(
            count = 2,
            artObjects = listOf(
                ArtResponse(
                    id = "id1",
                    objectNumber = "objectNumber1",
                    title = "title1",
                    principalOrFirstMaker = "principalOrFirstMaker1",
                    webImage = ImageResponse(
                        guid = "",
                        offsetPercentageX = 0,
                        offsetPercentageY = 0,
                        width = 0,
                        height = 0,
                        url = "url1",
                    ),
                ),
                ArtResponse(
                    id = "id2",
                    objectNumber = "objectNumber2",
                    title = "title2",
                    principalOrFirstMaker = "principalOrFirstMaker2",
                    webImage = ImageResponse(
                        guid = "",
                        offsetPercentageX = 0,
                        offsetPercentageY = 0,
                        width = 0,
                        height = 0,
                        url = "url2",
                    ),
                ),
            )
        )

        // When
        val actual = artCollectionMapper.map(response)

        // Then
        val expected = response.artObjects.map {
            ArtItem(
                it.objectNumber,
                it.title,
                it.principalOrFirstMaker,
                it.webImage.url
            )
        }

        assertEquals(expected, actual)
    }

}