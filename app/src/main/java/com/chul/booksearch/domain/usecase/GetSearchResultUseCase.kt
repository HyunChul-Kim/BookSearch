package com.chul.booksearch.domain.usecase

import com.chul.booksearch.data.model.Books
import com.chul.booksearch.data.model.SearchResponse
import com.chul.booksearch.domain.repository.BookRepository
import com.chul.booksearch.data.model.Result
import kotlinx.coroutines.*

class GetSearchResultUseCase(private val repository: BookRepository) {
    suspend fun invoke(query: String, page: Int): Result<SearchResponse> {
        return repository.getSearchResult(query, page)
    }

    suspend fun invokeWithNot(query1: String, query2: String, page: Int): Result<SearchResponse> {
        val result = repository.getSearchResult(query1, page)
        if(result is Result.Success) {
            result.data.books = result.data.books?.filter { !it.title.contains(query2) }
        }
        return result
    }

    suspend fun invokeWithOr(query1: String, query2: String, page: Int): Result<SearchResponse> {
        return coroutineScope {
            var error = "0"
            var total = 0
            var nextPage = 0
            val books = ArrayList<Books>()
            val result1 = async(Dispatchers.IO) {
                repository.getSearchResult(query1, page)
            }
            val result2 = async(Dispatchers.IO) {
                repository.getSearchResult(query2, page)
            }
            awaitAll(result1, result2).forEach { result ->
                if(result is Result.Success) {
                    total += result.data.total
                    nextPage = result.data.page
                    result.data.books?.let {
                        books.addAll(it)
                    }
                    //books.addAll(result.data._books)
                }
            }
            Result.Success(SearchResponse("0", total.toString(), nextPage.toString(), books))
        }
    }
}