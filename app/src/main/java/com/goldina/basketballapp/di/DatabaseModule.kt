package com.goldina.basketballapp.di

import android.app.Application
import androidx.room.Room
import com.goldina.basketballapp.db.FavouriteDAO
import com.goldina.basketballapp.db.FavouriteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application, callback: FavouriteDatabase.Callback): FavouriteDatabase{
        return Room.databaseBuilder(application, FavouriteDatabase::class.java, "fav_database")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    }

    @Provides
    fun provideArticleDao(db: FavouriteDatabase): FavouriteDAO{
        return db.getFavMatches()
    }
}