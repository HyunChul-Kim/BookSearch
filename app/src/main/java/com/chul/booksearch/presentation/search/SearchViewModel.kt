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

    private var currentQuery = ""
    val query = MutableLiveData<String>()

    private var _booksItemClickEvent = MutableLiveData<String>()
    val booksItemClickEvent: LiveData<String> = _booksItemClickEvent

    private var _networkExceptionEvent = MutableLiveData<Unit>()
    val networkExceptionEvent: LiveData<Unit> = _networkExceptionEvent

    private var _loadingEvent = MutableLiveData<Boolean>()
    val loadingEvent: LiveData<Boolean> = _loadingEvent

    var page = 1
    var totalCount = 0

    fun onSearch() {
        currentQuery = query.value.toString()
        if(currentQuery.isEmpty()) return

        _loadingEvent.value = true

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
        totalCount = 0
    }

    private fun requestSearch() {
        if(!networkManager.isNetworkConnected()) {
            _networkExceptionEvent.value = Unit
            _loadingEvent.value = false
            return
        }
        viewModelScope.launch {
            val response: Result<SearchResponse> = getSearchResultUseCase.invoke(currentQuery, page, true)
            if(response is Success) {
                totalCount = response.data.total
                response.data.books?.let {
                    _result.value = it as ArrayList
                }
            }
            _loadingEvent.value = false
        }
    }

    private fun requestPageSearch() {
        if(!networkManager.isNetworkConnected()) {
            _networkExceptionEvent.value = Unit
            return
        }
        viewModelScope.launch {
            val response:  Result<SearchResponse> = getSearchResultUseCase.invoke(currentQuery, page, false)
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
        const val LIMIT_QUERY_SIZE = 2
    }
}