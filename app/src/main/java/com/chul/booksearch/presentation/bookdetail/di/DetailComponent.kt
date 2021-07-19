package com.chul.booksearch.presentation.bookdetail.di

import com.chul.booksearch.presentation.bookdetail.DetailActivity
import com.chul.booksearch.presentation.di.scope.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [DetailModule::class])
interface DetailComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): DetailComponent
    }

    fun inject(activity: DetailActivity)
}