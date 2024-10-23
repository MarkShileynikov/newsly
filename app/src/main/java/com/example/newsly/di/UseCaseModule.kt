package com.example.newsly.di

import com.example.newsly.domain.repository.NewsRepository
import com.example.newsly.domain.usecase.FetchFullNewsUseCase
import com.example.newsly.domain.usecase.FetchNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    fun provideFetchNewsUseCase(repository: NewsRepository): FetchNewsUseCase {
        return FetchNewsUseCase(repository)
    }

    @Provides
    fun provideFetchFullNewsUseCase(repository: NewsRepository): FetchFullNewsUseCase {
        return FetchFullNewsUseCase(repository)
    }

}