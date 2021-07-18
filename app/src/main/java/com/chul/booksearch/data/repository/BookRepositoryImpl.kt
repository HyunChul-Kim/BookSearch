package com.chul.booksearch.data.repository

import com.chul.booksearch.data.model.BooksDetail
import com.chul.booksearch.data.model.SearchResponse
import com.chul.booksearch.data.datasource.BookRemoteDataSource
import com.chul.booksearch.data.model.Result
import com.chul.booksearch.domain.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookRepositoryImpl(private val dataSource: BookRemoteDataSource): BookRepository {
    override suspend fun getSearchResult(query: String, page: Int): Result<SearchResponse> = withContext(Dispatchers.IO) {
        return@withContext dataSource.getSearchResult(query, page)
    }

    override suspend fun getBooksDetail(isbn13: String): Result<BooksDetail> = withContext(Dispatchers.IO) {
        return@withContext dataSource.getBooksDetail(isbn13)
    }
}