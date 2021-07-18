package com.chul.booksearch.data.model

import com.google.gson.annotations.SerializedName

data class BooksDetail(
    @SerializedName("error")
    private val _error: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("subtitle")
    val subtitle: String?,
    @SerializedName("authors")
    val authors: String?,
    @SerializedName("publisher")
    val publisher: String?,
    @SerializedName("language")
    val language: String?,
    @SerializedName("isbn10")
    val isbn10: String?,
    @SerializedName("isbn13")
    val isbn13: String?,
    @SerializedName("pages")
    val pages: String?,
    @SerializedName("years")
    val years: String?,
    @SerializedName("rating")
    val rating: String?,
    @SerializedName("desc")
    val desc: String?,
    @SerializedName("price")
    val price: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("url")
    val url: String?
) {
    val error: String get() = _error ?: ""
}
