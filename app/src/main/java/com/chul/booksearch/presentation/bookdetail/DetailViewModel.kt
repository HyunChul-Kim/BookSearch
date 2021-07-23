package com.chul.booksearch.presentation.bookdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chul.booksearch.data.model.BooksDetail
import com.chul.booksearch.data.model.Result
import com.chul.booksearch.data.model.Result.Success
import com.chul.booksearch.domain.usecase.GetBooksDetailUseCase
import com.chul.booksearch.presentation.util.NetworkManager
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val getBooksDetailUseCase: GetBooksDetailUseCase,
    private val networkManager: NetworkManager): ViewModel() {

    private val _booksDetail = MutableLiveData<BooksDetail>()
    val booksDetail: LiveData<BooksDetail> = _booksDetail

    private val _dataLoaded = MutableLiveData<Boolean>()
    val dataLoaded: LiveData<Boolean> = _dataLoaded

    private var _networkExceptionEvent = MutableLiveData<Unit>()
    val networkExceptionEvent: LiveData<Unit> = _networkExceptionEvent

    fun requestDetail(isbn13: String) {
        if(!networkManager.isNetworkConnected()) {
            _networkExceptionEvent.value = Unit
            return
        }
        if(isbn13.isNotEmpty()) {
            viewModelScope.launch {
                val result: Result<BooksDetail> = getBooksDetailUseCase.invoke(isbn13)
                if(result is Success) {
                    _booksDetail.value = result.data
                    _dataLoaded.value = true
                }
            }
        }
    }
}