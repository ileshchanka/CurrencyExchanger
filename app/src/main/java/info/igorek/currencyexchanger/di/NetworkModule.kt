package info.igorek.currencyexchanger.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import info.igorek.currencyexchanger.data.network.ApiService
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {
        private const val TIMEOUT_SECONDS = 60L
        private const val BASE_API_URL = "https://developers.paysera.com/"
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideBaseRestApiService(
        okHttpClient: OkHttpClient,
        gsonFactory: Converter.Factory,
    ): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_API_URL)
            .addConverterFactory(gsonFactory)
            .client(okHttpClient)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}
