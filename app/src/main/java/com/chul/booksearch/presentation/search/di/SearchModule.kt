package com.chul.booksearch.presentation.search.di

import androidx.lifecycle.ViewModel
import com.chul.booksearch.presentation.di.ViewModelKey
import com.chul.booksearch.presentation.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SearchModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindViewModel(viewModel: SearchViewModel): ViewModel
}