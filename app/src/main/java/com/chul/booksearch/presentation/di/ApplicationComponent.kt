package com.chul.booksearch.presentation.di

import android.content.Context
import com.chul.booksearch.presentation.bookdetail.di.DetailComponent
import com.chul.booksearch.presentation.di.module.*
import com.chul.booksearch.presentation.search.di.SearchComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(modules = [
    NetworkModule::class,
    DataSourceModule::class,
    RepositoryModule::class,
    UseCaseModule::class,
    ViewModelBuilderModule::class,
    SubComponentModule::class])
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }

    fun searchComponent(): SearchComponent.Factory
    fun detailComponent(): DetailComponent.Factory
}

@Module(subcomponents = [SearchComponent::class, DetailComponent::class])
object SubComponentModule