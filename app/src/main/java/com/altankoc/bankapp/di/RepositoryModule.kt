package com.altankoc.bankapp.di

import com.altankoc.bankapp.data.datasource.RemoteDataSource
import com.altankoc.bankapp.data.repository.BankRepositoryImpl
import com.altankoc.bankapp.domain.repository.BankRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
    @Singleton
    fun provideBankRepository(bankDataSource: RemoteDataSource): BankRepository {
        return BankRepositoryImpl(bankDataSource)
    }

}