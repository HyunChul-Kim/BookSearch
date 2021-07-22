package com.chul.booksearch.presentation.search

import androidx.lifecycle.*
import com.chul.booksearch.data.model.Books
import com.chul.booksearch.data.model.SearchResponse
import com.chul.booksearch.data.model.Result
import com.chul.booksearch.data.model.Result.Success
import com.chul.booksearch.domain.usecase.GetSearchResultUseCase
import com.chul.booksearch.presentation.util.NetworkManager
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val getSearchResultUseCase: GetSearchResultUseCase,
    private val networkManager: NetworkManager): ViewModel() {

    private val _result = MutableLiveData<MutableList<Books>>()
    val result: LiveData<MutableList<Books>> = _result

    private var queries = listOf<String>()
    private var originalQuery = ""
    val query = MutableLiveData<String>()

    private var _booksItemClickEvent = MutableLiveData<String>()
    val booksItemClickEvent: LiveData<String> = _booksItemClickEvent

    private var _networkExceptionEvent = MutableLiveData<Unit>()
    val networkExceptionEvent: LiveData<Unit> = _networkExceptionEvent

    var page = 1
    var totalCount = 0
    var opType = OP_TYPE_NONE

    fun onSearch() {
        originalQuery = query.value.toString()
        if(originalQuery.isEmpty()) return
        init()
        updateOperator(originalQuery)
        updateQuery()
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
        totalCount = 0
        opType = OP_TYPE_NONE
    }

    private fun updateOperator(query: String) {
        opType = when {
            query.contains("|") -> OP_TYPE_OR
            query.contains("-") -> OP_TYPE_NOT
            else -> OP_TYPE_NONE
        }
    }

    private fun updateQuery() {
        val queryList = when(opType) {
            OP_TYPE_OR -> originalQuery.split("|")
            OP_TYPE_NOT -> originalQuery.split("-")
            else -> listOf(originalQuery)
        }
        queries = if(queryList.size > LIMIT_QUERY_SIZE) queryList.subList(0, LIMIT_QUERY_SIZE) else queryList
    }

    private fun requestSearch() {
        if(!networkManager.isNetworkConnected()) {
            _networkExceptionEvent.value = Unit
            return
        }
        viewModelScope.launch {
            val response: Result<SearchResponse> = when(opType) {
                OP_TYPE_OR -> {
                    getSearchResultUseCase.invokeWithOr(queries[0], queries[1], page)
                }
                OP_TYPE_NOT -> {
                    getSearchResultUseCase.invokeWithNot(queries[0], queries[1], page)
                }
                else -> {
                    getSearchResultUseCase.invoke(originalQuery, page)
                }
            }
            if(response is Success) {
                totalCount = response.data.total
                response.data.books?.let {
                    _result.value = it as ArrayList
                }
            }
        }
    }

    private fun requestPageSearch() {
        if(!networkManager.isNetworkConnected()) {
            _networkExceptionEvent.value = Unit
            return
        }
        viewModelScope.launch {
            val response:  Result<SearchResponse> = when(opType) {
                OP_TYPE_OR -> {
                    getSearchResultUseCase.invokeWithOr(queries[0], queries[1], page)
                }
                OP_TYPE_NOT -> {
                    getSearchResultUseCase.invokeWithNot(queries[0], queries[1], page)
                }
                else -> {
                    getSearchResultUseCase.invoke(originalQuery, page)
                }
            }
            if(response is Success) {
                response.data.books?.let {
                    val list = _result.value?.toMutableList()
                    list?.addAll(it as ArrayList)
                    _result.value = list
                }
            }
        }
    }

    companion object {
        const val OP_TYPE_NONE = 0
        const val OP_TYPE_OR = 1
        const val OP_TYPE_NOT = 2

        const val LIMIT_QUERY_SIZE = 2
    }
}