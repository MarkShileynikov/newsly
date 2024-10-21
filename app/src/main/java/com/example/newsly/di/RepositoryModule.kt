package com.example.newsly.di

import com.example.newsly.data.repository.NewsRepositoryImpl
import com.example.newsly.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository =
        newsRepositoryImpl
}