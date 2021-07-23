package com.chul.booksearch.data.repository

import com.chul.booksearch.data.model.BooksDetail
import com.chul.booksearch.data.model.SearchResponse
import com.chul.booksearch.data.datasource.BookRemoteDataSource
import com.chul.booksearch.data.model.Result
import com.chul.booksearch.domain.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap

class BookRepositoryImpl(private val dataSource: BookRemoteDataSource): BookRepository {

    private val cachedBooks = ConcurrentHashMap<String, HashMap<Int, SearchResponse>>()
    private val cachedDetails = ConcurrentHashMap<String, BooksDetail>()

    override suspend fun getSearchResult(query: String, page: Int): Result<SearchResponse> = withContext(Dispatchers.IO) {
        cachedBooks[query]?.get(page)?.let {
            return@withContext Result.Success(it)
        }
        try {
            val response = dataSource.getSearchResult(query, page)
            val errorCode = response.error
            return@withContext if ("0" == errorCode) {
                var cached = cachedBooks[query]
                if(cached == null) {
                    cached = HashMap()
                }
                cached[page] = response
                cachedBooks[query] = cached
                Result.Success(response)
            } else {
                Result.Error(Exception("error code is not zero"), errorCode)
            }
        } catch (e: Exception) {
            Result.Error(e, "-1")
        }
    }

    override suspend fun getBooksDetail(isbn13: String): Result<BooksDetail> = withContext(Dispatchers.IO) {
        cachedDetails[isbn13]?.let {
            return@withContext Result.Success(it)
        }
        try {
            val response = dataSource.getBooksDetail(isbn13)
            val errorCode = response.error
            return@withContext if ("0" == errorCode) {
                cachedDetails[isbn13] = response
                Result.Success(response)
            } else {
                Result.Error(Exception("error code is not zero"), errorCode)
            }
        } catch (e: Exception) {
            Result.Error(e, "-1")
        }
    }
}