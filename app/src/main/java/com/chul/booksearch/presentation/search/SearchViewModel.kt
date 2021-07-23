package com.chul.booksearch.presentation.search

import androidx.lifecycle.*
import com.chul.booksearch.data.model.Books
import com.chul.booksearch.data.model.SearchResponse
import com.chul.booksearch.data.model.Result
import com.chul.booksearch.data.model.Result.Success
import com.chul.booksearch.data.model.Result.Error
import com.chul.booksearch.domain.usecase.GetSearchResultUseCase
import com.chul.booksearch.presentation.util.Event
import com.chul.booksearch.presentation.util.NetworkManager
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val getSearchResultUseCase: GetSearchResultUseCase,
    private val networkManager: NetworkManager): ViewModel() {

    private val _result = MutableLiveData<List<Books>>()
    val result: LiveData<List<Books>> = _result

    private var currentQuery = ""
    val query = MutableLiveData<String>()

    private var _booksItemClickEvent = MutableLiveData<Event<String>>()
    val booksItemClickEvent: LiveData<Event<String>> = _booksItemClickEvent

    private var _networkExceptionEvent = MutableLiveData<Event<ErrorType>>()
    val networkExceptionEvent: LiveData<Event<ErrorType>> = _networkExceptionEvent

    private var _loadingEvent = MutableLiveData<Boolean>()
    val loadingEvent: LiveData<Boolean> = _loadingEvent

    var page = 1
    var totalCount = 0

    fun onSearch() {
        currentQuery = query.value.toString()
        if(currentQuery.isEmpty()) return

        _loadingEvent.value = true

        initialize()
        requestSearch()
    }

    fun onSearchMore() {
        page++
        requestPageSearch()
    }

    fun onBooksItemClick(isbn13: String) {
        if(isbn13.isNotEmpty()) {
            _booksItemClickEvent.value = Event(isbn13)
        }
    }

    private fun initialize() {
        page = 1
        totalCount = 0
        _result.value = emptyList()
    }

    private fun requestSearch() {
        if(!networkManager.isNetworkConnected()) {
            _networkExceptionEvent.value = Event(ErrorType.NETWORK_ERROR)
            _loadingEvent.value = false
            return
        }
        viewModelScope.launch {
            val response: Result<SearchResponse> = getSearchResultUseCase.invoke(currentQuery, page, true)
            when (response) {
                is Success -> {
                    totalCount = response.data.total
                    response.data.books?.let {
                        _result.value = it as ArrayList
                    }
                }
                is Error -> {
                    _result.value = emptyList()
                    _networkExceptionEvent.value = Event(ErrorType.REQUEST_ERROR)
                }
            }
            _loadingEvent.value = false
        }
    }

    private fun requestPageSearch() {
        if(!networkManager.isNetworkConnected()) {
            _networkExceptionEvent.value = Event(ErrorType.NETWORK_ERROR)
            return
        }
        viewModelScope.launch {
            val response:  Result<SearchResponse> = getSearchResultUseCase.invoke(currentQuery, page, false)
            when(response) {
                is Success -> {
                    response.data.books?.let {
                        val list = _result.value?.toMutableList()
                        list?.addAll(it as ArrayList)
                        _result.value = list
                    }
                }
                is Error -> {
                    _networkExceptionEvent.value = Event(ErrorType.REQUEST_ERROR)
                }
            }
        }
    }

    companion object {
        const val LIMIT_QUERY_SIZE = 2
    }
}