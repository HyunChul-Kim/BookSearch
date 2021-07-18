package com.chul.booksearch.presentation.di.module

import com.chul.booksearch.data.datasource.BookRemoteDataSource
import com.chul.booksearch.data.repository.BookRepositoryImpl
import com.chul.booksearch.domain.repository.BookRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideBookRepository(dataSource: BookRemoteDataSource): BookRepository {
        return BookRepositoryImpl(dataSource)
    }
}