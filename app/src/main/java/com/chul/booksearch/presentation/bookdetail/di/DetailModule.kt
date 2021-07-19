package com.chul.booksearch.presentation.bookdetail.di

import androidx.lifecycle.ViewModel
import com.chul.booksearch.presentation.bookdetail.DetailViewModel
import com.chul.booksearch.presentation.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class DetailModule {

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindViewModel(viewModel: DetailViewModel): ViewModel
}