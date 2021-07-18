package com.chul.booksearch.data.api

import com.chul.booksearch.data.model.BooksDetail
import com.chul.booksearch.data.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BookService {

    @GET("search/{query}/{page}")
    suspend fun getSearchResult(@Path("query") query: String, @Path("page") page: Int): SearchResponse

    @GET("books/{isbn13}")
    suspend fun getBooksDetail(@Path("isbn13") isbn13: String): BooksDetail
}