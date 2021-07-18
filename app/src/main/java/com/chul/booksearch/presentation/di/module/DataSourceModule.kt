package com.chul.booksearch.presentation.di.module

import com.chul.booksearch.data.api.BookService
import com.chul.booksearch.data.datasource.BookRemoteDataSource
import com.chul.booksearch.data.datasource.BookRemoteDataSourceImpl
import dagger.Module
import dagger.Provides

@Module
class DataSourceModule {
    @Provides
    fun provideBookRemoteDataSource(service: BookService): BookRemoteDataSource {
        return BookRemoteDataSourceImpl(service)
    }
}