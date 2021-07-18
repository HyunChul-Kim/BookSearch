package com.chul.booksearch.data.model

import java.lang.Exception

sealed class Result<out R> {
    data class Success<out T>(val data: T): Result<T>()
    data class Error(val exception: Exception, val error: String): Result<Nothing>()
}