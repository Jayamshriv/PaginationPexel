package com.example.paginationpexel.di

import android.content.Context
import androidx.room.Room
import com.example.paginationpexel.ApiService
import com.example.paginationpexel.Constants
import com.example.paginationpexel.local.PexelDao
import com.example.paginationpexel.local.PexelDatabase
import com.example.paginationpexel.mappers.ControlledConverters
import com.example.paginationpexel.repository.PexelRepository
import com.example.paginationpexel.repository.PexelRepositoryImpl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): PexelDatabase {
        return Room
            .databaseBuilder(appContext, PexelDatabase::class.java, "pexel_database")
            .fallbackToDestructiveMigration()
            .addTypeConverter(ControlledConverters(Gson()))
            .build()
    }

//    @Provides
//    fun providePexelDao(database: PexelDatabase): PexelDao {
//        return database.dao
//    }

    @Provides
    @Singleton
    fun providePexelRepository(
        apiService: ApiService,
        database: PexelDatabase
    )
    : PexelRepository {
        return PexelRepositoryImpl(apiService, database)
    }
}