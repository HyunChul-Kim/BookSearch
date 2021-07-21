package com.chul.booksearch.data.datasource

import com.chul.booksearch.data.api.BookService
import com.chul.booksearch.data.model.BooksDetail
import com.chul.booksearch.data.model.SearchResponse
import com.chul.booksearch.data.model.Result
import com.chul.booksearch.data.model.Result.Success
import com.chul.booksearch.data.model.Result.Error
import com.chul.booksearch.presentation.util.LogHelper
import java.lang.Exception

class BookRemoteDataSourceImpl(private val api: BookService): BookRemoteDataSource {
    override suspend fun getSearchResult(query: String, page: Int): Result<SearchResponse> {
        val result = api.getSearchResult(query, page)
        val errorCode = result.error
        return if("0" == errorCode) {
            LogHelper.log("response $query")
            Success(result)
        } else {
            Error(Exception("error code is not zero"), errorCode)
        }
    }

    override suspend fun getBooksDetail(isbn13: String): Result<BooksDetail> {
        val detail = api.getBooksDetail(isbn13)
        val errorCode = detail.error
        return if("0" == errorCode) {
            Success(detail)
        } else {
            Error(Exception("error code is not zero"), errorCode)
        }
    }
}