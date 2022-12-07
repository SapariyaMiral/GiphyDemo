package com.giphyapp.di

import android.content.Context
import androidx.room.Room
import com.giphyapp.data.database.AppDatabase
import com.giphyapp.MyBaseApp
import com.giphyapp.network.ApiClient
import com.giphyapp.network.RetrofitService
import com.giphyapp.utils.DB_NAME
import com.giphyapp.utils.JLog

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApiClient(): ApiClient = ApiClient().setLogger {
        JLog.d("RETROFIT :: ", it)
    }

    @Singleton
    @Provides
    fun provideAuthApi(apiClient: ApiClient): RetrofitService =
        apiClient.createService(RetrofitService::class.java)


    @Singleton
    @Provides
    fun provideRoomDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        DB_NAME
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideAppDao(db: AppDatabase) = db.getAppDao()

}