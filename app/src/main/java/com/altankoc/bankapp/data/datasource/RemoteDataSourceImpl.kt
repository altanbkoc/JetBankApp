package com.altankoc.bankapp.data.datasource

import com.altankoc.bankapp.data.model.BankData
import com.altankoc.bankapp.data.network.BankApiService
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val bankApiService: BankApiService
) : RemoteDataSource{

    override suspend fun getBankDataSource(): BankData {
        return bankApiService.getBankDataNetwork()
    }
}