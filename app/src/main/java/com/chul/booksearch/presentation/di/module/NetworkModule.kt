package com.chul.booksearch.presentation.di.module

import android.content.Context
import com.chul.booksearch.data.api.BookService
import com.chul.booksearch.presentation.util.NetworkManager
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.delay
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.itbook.store/1.0/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build())
            .build()
    }

    @Singleton
    @Provides
    fun provideSearchService(retrofit: Retrofit): BookService {
        return retrofit.create(BookService::class.java)
    }

    @Singleton
    @Provides
    fun provideNetworkManager(context: Context): NetworkManager {
        return NetworkManager(context.applicationContext)
    }
}