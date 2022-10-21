package nl.yoerivanhoek.rijksdemo.data.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit

fun apiModule(baseUrl: HttpUrl) = module {

    single {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(RijksAuthInterceptor())
            .build()
    }

    single {
        val format = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(get())
            .addConverterFactory(format.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    single { get<Retrofit>(Retrofit::class).create(CollectionApiService::class.java) }

}