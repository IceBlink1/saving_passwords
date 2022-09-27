package com.example.savingpasswords.data

import android.content.Context
import androidx.room.Room
import com.example.savingpasswords.domain.AccountRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * DI Data Module class
 *
 */
@InstallIn(SingletonComponent::class)
@Module
class DataModule {
    @Provides
    fun provideAccountDao(appDatabase: AccountDatabase): AccountDao {
        return appDatabase.accountDatabase()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AccountDatabase {
        return Room.databaseBuilder(
            appContext,
            AccountDatabase::class.java,
            "RssReader"
        ).build()
    }

    @Provides
    fun provideRepoImpl(accountRepositoryImpl: AccountRepositoryImpl): AccountRepository {
        return accountRepositoryImpl
    }
}