package com.altankoc.bankapp.data.repository

import com.altankoc.bankapp.data.datasource.RemoteDataSource
import com.altankoc.bankapp.data.model.BankData
import com.altankoc.bankapp.domain.repository.BankRepository
import com.altankoc.bankapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BankRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : BankRepository {

    override fun getBankData(): Flow<Resource<BankData>> = flow {
        emit(Resource.Loading())
        try {
            val response = remoteDataSource.getBankDataSource()
            emit(Resource.Success(response))
        }catch (e: Exception){
            emit(Resource.Error("Error: ${e.message}"))
        }
    }
}