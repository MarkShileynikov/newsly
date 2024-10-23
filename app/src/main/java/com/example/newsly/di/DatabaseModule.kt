package com.example.newsly.di

import android.content.Context
import androidx.room.Room
import com.example.newsly.data.database.Database
import com.example.newsly.data.database.dao.BookmarkDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : Database {
        return Room.databaseBuilder(
            context.applicationContext,
            Database::class.java,
            "database"
        ).build()
    }

    @Provides
    fun provideNewsDao(database: Database) : BookmarkDao = database.bookmarkDao
}