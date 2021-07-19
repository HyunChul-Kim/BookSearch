package com.chul.booksearch.domain.usecase

import com.chul.booksearch.data.model.BooksDetail
import com.chul.booksearch.domain.repository.BookRepository
import com.chul.booksearch.data.model.Result

class GetBooksDetailUseCase(private val repository: BookRepository) {
    suspend fun invoke(isbn13: String): Result<BooksDetail> {
        return repository.getBooksDetail(isbn13)
    }
}