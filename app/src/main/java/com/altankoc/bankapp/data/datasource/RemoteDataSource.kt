package com.altankoc.bankapp.data.datasource

import com.altankoc.bankapp.data.model.BankData

interface RemoteDataSource {
    suspend fun getBankDataSource(): BankData
}