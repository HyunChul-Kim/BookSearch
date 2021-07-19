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

    private val _result: MutableLiveData<MutableList<Books>> = MutableLiveData()
    val result: LiveData<MutableList<Books>> = _result

    private var currentQuery = ""
    val query = MutableLiveData<String>()

    var totalCount = 0
    var page = 1

    fun search() {
        currentQuery = query.value.toString()
        if(currentQuery.isEmpty()) return
        init()
        requestSearch()
    }

    fun searchMore() {
        page++
        requestPageSearch()
    }

    private fun init() {
        page = 1
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