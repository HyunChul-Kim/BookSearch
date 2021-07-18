package com.chul.booksearch

import android.app.Application
import com.chul.booksearch.presentation.di.ApplicationComponent
import com.chul.booksearch.presentation.di.DaggerApplicationComponent

class BookApplication: Application() {

    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(applicationContext)
    }
}