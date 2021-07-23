package com.chul.booksearch

import androidx.lifecycle.Observer
import com.chul.booksearch.data.datasource.BookRemoteDataSourceImpl
import com.chul.booksearch.data.model.Result
import com.chul.booksearch.data.model.SearchResponse
import com.chul.booksearch.data.repository.BookRepositoryImpl
import com.chul.booksearch.domain.usecase.GetSearchResultUseCase
import com.chul.booksearch.presentation.search.SearchViewModel
import com.chul.booksearch.presentation.util.NetworkManager
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest {
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Mock
    private lateinit var getSearchResultUseCase: GetSearchResultUseCase

    @Mock
    private lateinit var networkManager: NetworkManager

    @Mock
    private lateinit var observer: Observer<Result<SearchResponse>>

    @Mock
    private lateinit var source: BookRemoteDataSourceImpl

    @Mock
    private lateinit var respository: BookRepositoryImpl

    private val errorResponse = Result.Error(Exception("error code is not zero"), "-1")

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        val exception: HttpException = mock(HttpException::class.java)
        whenever(exception.code()).thenReturn(401)
        runBlocking {
            whenever(source.getSearchResult(anyString(), anyInt())).thenThrow(exception)
        }
        respository = BookRepositoryImpl(source)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `test for http exception`() = runBlocking {
        val response = respository.getSearchResult("", 1)
        assertEquals(errorResponse, response)
    }

}