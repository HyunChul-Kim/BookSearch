package com.chul.booksearch

import okhttp3.mockwebserver.MockWebServer
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private var server = MockWebServer()

    @Before
    fun setup() {
        server.start()
    }
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}