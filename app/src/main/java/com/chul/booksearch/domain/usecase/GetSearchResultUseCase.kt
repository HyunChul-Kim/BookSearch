package com.chul.booksearch.domain.usecase

import com.chul.booksearch.data.model.SearchResponse
import com.chul.booksearch.domain.repository.BookRepository
import com.chul.booksearch.data.model.Result

class GetSearchResultUseCase(private val repository: BookRepository) {
    suspend fun invoke(query: String, page: Int): Result<SearchResponse> {
        return repository.getSearchResult(query, page)
    }
}