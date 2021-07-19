package com.chul.booksearch.presentation.di.module

import com.chul.booksearch.domain.repository.BookRepository
import com.chul.booksearch.domain.usecase.GetBooksDetailUseCase
import com.chul.booksearch.domain.usecase.GetSearchResultUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun provideGetSearchResultUseCase(repository: BookRepository): GetSearchResultUseCase {
        return GetSearchResultUseCase(repository)
    }

    @Provides
    fun provideGetBooksDetailUseCase(repository: BookRepository): GetBooksDetailUseCase {
        return GetBooksDetailUseCase(repository)
    }
}