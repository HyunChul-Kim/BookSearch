package com.chul.booksearch.presentation.di.module

import androidx.lifecycle.ViewModelProvider
import com.chul.booksearch.presentation.di.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelBuilderModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}