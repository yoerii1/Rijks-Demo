package nl.yoerivanhoek.rijksdemo

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import nl.yoerivanhoek.rijksdemo.data.ArtItemRepositoryImpl
import nl.yoerivanhoek.rijksdemo.data.CollectionPagingSource
import nl.yoerivanhoek.rijksdemo.data.CollectionRemoteDataSource
import nl.yoerivanhoek.rijksdemo.data.api.RijksApi
import nl.yoerivanhoek.rijksdemo.data.api.RijksAuthInterceptor
import nl.yoerivanhoek.rijksdemo.data.mapper.ArtCollectionMapper
import nl.yoerivanhoek.rijksdemo.data.mapper.ArtDetailsMapper
import nl.yoerivanhoek.rijksdemo.domain.ArtItemRepository
import nl.yoerivanhoek.rijksdemo.domain.usecase.GetArtDetails
import nl.yoerivanhoek.rijksdemo.domain.usecase.GetArtItems
import nl.yoerivanhoek.rijksdemo.ui.detail.ArtDetailsViewModel
import nl.yoerivanhoek.rijksdemo.ui.list.ArtOverviewViewModel
import nl.yoerivanhoek.rijksdemo.ui.list.ArtUiModelMapper
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

val artModule = module {
    viewModel {
        ArtOverviewViewModel(
            getArtItems = get(),
            artUiModelMapper = get()
        )
    }
    viewModel { (artId: String) ->
        ArtDetailsViewModel(
            artId = artId,
            getArtDetails = get()
        )
    }

    factory {
        GetArtItems(
            artItemRepository = get()
        )
    }

    factory {
        GetArtDetails(
            collectionRepository = get()
        )
    }

    factory<ArtItemRepository> {
        ArtItemRepositoryImpl(
            collectionPagingSource = get(),
            collectionRemoteDataSource = get()
        )
    }

    factory {
        CollectionPagingSource(
            collectionRemoteDataSource = get()
        )
    }

    factory {
        CollectionRemoteDataSource(
            collectionApiService = get(),
            artDetailsMapper = get(),
            collectionMapper = get()
        )
    }

    single {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(RijksAuthInterceptor())
            .addInterceptor(get<ChuckerInterceptor>())
            .build()
    }

    single {
        val format = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }

        Retrofit.Builder()
            .baseUrl("https://www.rijksmuseum.nl/")
            .client(get())
            .addConverterFactory(format.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    single { get<Retrofit>().create(RijksApi::class.java) }

    single {
        ArtCollectionMapper
    }

    single {
        ArtDetailsMapper
    }

    single {
        ArtUiModelMapper
    }

    single {
        ChuckerInterceptor.Builder(androidApplication())
            .build()
    }
}