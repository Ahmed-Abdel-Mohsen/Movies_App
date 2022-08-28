package com.example.data.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.base.BuildConfig
import com.example.base.utils.Constants.DEFAULT_NETWORK_TIMEOUT_SECONDS
import com.example.base.utils.Constants.MOVIES_BASE_URL
import com.example.data.network.api.MoviesApi
import com.example.data.network.interceptors.DefaultInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Koin module for okHttp , retrofit and its api interfaces
 */
val networkModule = module {

    single(named(DI_MOVIES_BASE_URL)) { MOVIES_BASE_URL }

    fun provideGsonBuild(): GsonBuilder {
        return GsonBuilder()
    }

    single {
        provideGsonBuild()
    }

    fun provideOkHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    single {
        provideOkHttpLoggingInterceptor()
    }

    single {
        DefaultInterceptor()
    }

    fun provideChuckerInterceptor(context: Context): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context)
            .collector(ChuckerCollector(context))
            .build()
    }

    single {
        provideChuckerInterceptor(androidContext())
    }

    fun provideOkHttpClient(
        defaultInterceptor: DefaultInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        chuckerInterceptor: ChuckerInterceptor,
        timeout: Long = DEFAULT_NETWORK_TIMEOUT_SECONDS
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(timeout, TimeUnit.SECONDS)
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
            .addInterceptor(defaultInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(chuckerInterceptor)
            .build()
    }

    single { provideOkHttpClient(get(), get(), get()) }

    fun provideRetrofit(
        baseUrl: String,
        gsonBuilder: GsonBuilder,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create())).build()
    }

    single {
        provideRetrofit(
            get(named(DI_MOVIES_BASE_URL)),
            get(),
            get()
        )
    }

    factory {
        get<Retrofit>().create(MoviesApi::class.java)
    }

}

const val DI_MOVIES_BASE_URL = "MOVIES_BASE_URL"