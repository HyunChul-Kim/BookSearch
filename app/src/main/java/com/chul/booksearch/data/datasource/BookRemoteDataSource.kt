package com.chul.booksearch.data.datasource

import com.chul.booksearch.data.model.BooksDetail
import com.chul.booksearch.data.model.SearchResponse
import com.chul.booksearch.data.model.Result

interface BookRemoteDataSource {

    suspend fun getSearchResult(query: String, page: Int): SearchResponse

    suspend fun getBooksDetail(isbn13: String): BooksDetail
}