package nl.yoerivanhoek.rijksdemo.data.api

import nl.yoerivanhoek.rijksdemo.data.model.ArtDetailResponse
import nl.yoerivanhoek.rijksdemo.data.model.ArtCollectionResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CollectionApiService {

    @GET("/api/nl/collection?s=artist&imgonly=true")
    suspend fun getCollection(@Query("p") page: Int, @Query("ps") loadSize: Int): ArtCollectionResponse

    @GET("/api/nl/collection/{artId}")
    suspend fun getArtDetails(@Path("artId") artId: String): ArtDetailResponse

}