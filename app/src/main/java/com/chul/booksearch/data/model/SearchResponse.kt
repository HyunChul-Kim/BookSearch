package com.chul.booksearch.data.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("error")
    private val _error: String?,
    @SerializedName("total")
    private val _total: String?,
    @SerializedName("page")
    private val _page: String?,
    @SerializedName("books")
    val books: List<Books>?
) {
    val error: String get() = _error ?: ""
    val total: Int get() = _total?.toInt() ?: 0
    val page: Int get() = _page?.toInt() ?: 0
}