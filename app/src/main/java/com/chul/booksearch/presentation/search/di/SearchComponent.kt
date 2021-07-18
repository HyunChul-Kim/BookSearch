package com.chul.booksearch.presentation.search.di

import com.chul.booksearch.presentation.di.scope.ActivityScope
import com.chul.booksearch.presentation.search.SearchActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [SearchModule::class])
interface SearchComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): SearchComponent
    }

    fun inject(searchActivity: SearchActivity)
}