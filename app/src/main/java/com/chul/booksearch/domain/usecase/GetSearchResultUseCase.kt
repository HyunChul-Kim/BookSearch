package com.chul.booksearch.domain.usecase

import com.chul.booksearch.data.model.Books
import com.chul.booksearch.data.model.SearchResponse
import com.chul.booksearch.domain.repository.BookRepository
import com.chul.booksearch.data.model.Result
import com.chul.booksearch.presentation.search.OperatorType.*

import kotlinx.coroutines.*

class GetSearchResultUseCase(private val repository: BookRepository) {

    private var opType = OP_TYPE_NONE

    suspend fun invoke(query: String, page: Int, force: Boolean): Result<SearchResponse> = withContext(Dispatchers.Default) {
        if(force) {
            updateOperator(query)
        }
        when(opType) {
            OP_TYPE_OR -> {
                val queries = query.split("|")
                if(queries.size >= 2) {
                    or(queries[0], queries[1], page)
                } else {
                    repository.getSearchResult(query, page)
                }
            }
            OP_TYPE_NOT -> {
                val queries = query.split("-")
                if(queries.size >= 2) {
                    not(queries[0], queries[1], page)
                } else {
                    repository.getSearchResult(query, page)
                }
            }
            else -> repository.getSearchResult(query, page)
        }
    }

    suspend fun not(query1: String, query2: String, page: Int): Result<SearchResponse> {
        val result = repository.getSearchResult(query1, page)
        if(result is Result.Success) {
            result.data.books = result.data.books?.filter { !it.title.contains(query2, true) }
        }
        return result
    }

    suspend fun or(query1: String, query2: String, page: Int): Result<SearchResponse> {
        return supervisorScope {
            var total = 0
            var nextPage = 0
            val books = ArrayList<Books>()
            val result1 = async {
                repository.getSearchResult(query1, page)
            }
            val result2 = async {
                repository.getSearchResult(query2, page)
            }
            awaitAll(result1, result2).forEach { result ->
                if(result is Result.Success) {
                    total += result.data.total
                    nextPage = result.data.page
                    result.data.books?.let {
                        books.addAll(it)
                    }
                }
            }
            Result.Success(SearchResponse("0", total.toString(), nextPage.toString(), books))
        }
    }

    private fun updateOperator(query: String) {
        opType = when {
            query.contains("|") -> OP_TYPE_OR
            query.contains("-") -> OP_TYPE_NOT
            else -> OP_TYPE_NONE
        }
    }
}