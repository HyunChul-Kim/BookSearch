package com.chul.booksearch.data.datasource

import com.chul.booksearch.data.api.BookService
import com.chul.booksearch.data.model.BooksDetail
import com.chul.booksearch.data.model.SearchResponse

class BookRemoteDataSourceImpl(private val api: BookService): BookRemoteDataSource {
    override suspend fun getSearchResult(query: String, page: Int): SearchResponse {
        return api.getSearchResult(query, page)
    }

    override suspend fun getBooksDetail(isbn13: String): BooksDetail {
        return api.getBooksDetail(isbn13)
    }
}