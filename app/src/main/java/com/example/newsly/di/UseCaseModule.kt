package com.example.newsly.di

import com.example.newsly.domain.repository.NewsRepository
import com.example.newsly.domain.usecase.AddBookmarkUseCase
import com.example.newsly.domain.usecase.BookmarkCheckUseCase
import com.example.newsly.domain.usecase.DeleteBookmarkUseCase
import com.example.newsly.domain.usecase.FetchAllBookmarksUseCase
import com.example.newsly.domain.usecase.FetchBookmarkUseCase
import com.example.newsly.domain.usecase.FetchFullNewsUseCase
import com.example.newsly.domain.usecase.FetchNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideFetchNewsUseCase(repository: NewsRepository): FetchNewsUseCase {
        return FetchNewsUseCase(repository)
    }

    @Provides
    fun provideFetchFullNewsUseCase(repository: NewsRepository): FetchFullNewsUseCase {
        return FetchFullNewsUseCase(repository)
    }

    @Provides
    fun provideAddBookmarkUseCase(repository: NewsRepository): AddBookmarkUseCase {
        return AddBookmarkUseCase(repository)
    }

    @Provides
    fun provideBookmarkCheckUseCase(repository: NewsRepository): BookmarkCheckUseCase {
        return BookmarkCheckUseCase(repository)
    }

    @Provides
    fun provideDeleteBookmarkUseCase(repository: NewsRepository): DeleteBookmarkUseCase {
        return DeleteBookmarkUseCase(repository)
    }

    @Provides
    fun provideFetchAllBookmarksUseCase(repository: NewsRepository): FetchAllBookmarksUseCase {
        return FetchAllBookmarksUseCase(repository)
    }

    @Provides
    fun provideFetchBookmarkUseCase(repository: NewsRepository): FetchBookmarkUseCase {
        return FetchBookmarkUseCase(repository)
    }

}