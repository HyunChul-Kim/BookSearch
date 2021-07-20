package com.chul.booksearch.presentation.search

import androidx.lifecycle.*
import com.chul.booksearch.data.model.Books
import com.chul.booksearch.data.model.SearchResponse
import com.chul.booksearch.data.model.Result
import com.chul.booksearch.data.model.Result.Success
import com.chul.booksearch.domain.usecase.GetSearchResultUseCase
import com.chul.booksearch.presentation.util.LogHelper
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val getSearchResultUseCase: GetSearchResultUseCase): ViewModel() {

    private val _result = MutableLiveData<MutableList<Books>>()
    val result: LiveData<MutableList<Books>> = _result

    private var currentQuery = ""
    val query = MutableLiveData<String>()

    private var _booksItemClickEvent = MutableLiveData<String>()
    val booksItemClickEvent: LiveData<String> = _booksItemClickEvent

    var totalCount = 0
    var page = 1

    fun onSearch() {
        currentQuery = query.value.toString()
        if(currentQuery.isEmpty()) return
        init()
        requestSearch()
    }

    fun onSearchMore() {
        page++
        requestPageSearch()
    }

    fun onBooksItemClick(isbn13: String) {
        if(isbn13.isNotEmpty()) {
            _booksItemClickEvent.value = isbn13
        }
    }

    private fun init() {
        page = 1
    }

    private fun getKeywords(query: String): List<String> {
        val keywords = when {
            query.contains("|") -> {
                query.split("|")
            }
            query.contains("-") -> {
                query.split("-")
            }
            else -> {
                listOf(query)
            }
        }
        return keywords
    }

    private fun requestSearch() {
        viewModelScope.launch {
            val response: Result<SearchResponse> = getSearchResultUseCase.invoke(currentQuery, page)
            if(response is Success) {
                totalCount = response.data.total
                response.data.books?.let {
                    _result.value = it as ArrayList
                }
            }
        }
    }

    private fun requestPageSearch() {
        viewModelScope.launch {
            val response: Result<SearchResponse> = getSearchResultUseCase.invoke(currentQuery, page)
            if(response is Success) {
                response.data.books?.let {
                    val list = _result.value?.toMutableList()
                    list?.addAll(it as ArrayList)
                    _result.value = list
                }
            }
        }
    }
}