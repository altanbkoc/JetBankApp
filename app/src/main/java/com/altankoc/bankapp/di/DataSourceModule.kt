package com.altankoc.bankapp.di

import com.altankoc.bankapp.data.datasource.RemoteDataSource
import com.altankoc.bankapp.data.datasource.RemoteDataSourceImpl
import com.altankoc.bankapp.data.network.BankApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(bankApiService: BankApiService): RemoteDataSource {
        return RemoteDataSourceImpl(bankApiService)
    }
}