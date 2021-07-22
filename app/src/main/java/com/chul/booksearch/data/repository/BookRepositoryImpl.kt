package com.chul.booksearch.data.repository

import com.chul.booksearch.data.model.BooksDetail
import com.chul.booksearch.data.model.SearchResponse
import com.chul.booksearch.data.datasource.BookRemoteDataSource
import com.chul.booksearch.data.model.Result
import com.chul.booksearch.domain.repository.BookRepository
import com.chul.booksearch.presentation.util.LogHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class BookRepositoryImpl(private val dataSource: BookRemoteDataSource): BookRepository {
    override suspend fun getSearchResult(query: String, page: Int): Result<SearchResponse> = withContext(Dispatchers.IO) {
        val result = dataSource.getSearchResult(query, page)
        val errorCode = result.error
        return@withContext if("0" == errorCode) {
            LogHelper.log("response $query")
            Result.Success(result)
        } else {
            Result.Error(Exception("error code is not zero"), errorCode)
        }
    }

    override suspend fun getBooksDetail(isbn13: String): Result<BooksDetail> = withContext(Dispatchers.IO) {
        val detail = dataSource.getBooksDetail(isbn13)
        val errorCode = detail.error
        return@withContext if("0" == errorCode) {
            Result.Success(detail)
        } else {
            Result.Error(Exception("error code is not zero"), errorCode)
        }
    }
}