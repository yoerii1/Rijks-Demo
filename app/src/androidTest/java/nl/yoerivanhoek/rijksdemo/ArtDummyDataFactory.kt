package nl.yoerivanhoek.rijksdemo

import nl.yoerivanhoek.rijksdemo.data.model.*

object ArtDummyDataFactory {
    fun getCollectionResponse() = ArtCollectionResponse(
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

    fun getDetailsResponse() = ArtDetailResponse(
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
}