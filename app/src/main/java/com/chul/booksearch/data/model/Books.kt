package com.chul.booksearch.data.model

import com.google.gson.annotations.SerializedName

data class Books(
    @SerializedName("title")
    private val _title: String?,
    @SerializedName("subtitle")
    private val _subtitle: String?,
    @SerializedName("isbn13")
    private val _isbn13: String?,
    @SerializedName("price")
    private val _price: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("url")
    val url: String?
) {
    val title: String get() = _title ?: ""
    val subtitle: String get() = _subtitle ?: ""
    val isbn13: String get() = _isbn13 ?: ""
    val price: String get() = _price ?: ""
}