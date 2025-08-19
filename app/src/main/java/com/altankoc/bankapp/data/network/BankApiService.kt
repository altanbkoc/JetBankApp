package com.altankoc.bankapp.data.network

import com.altankoc.bankapp.data.model.BankData
import retrofit2.http.GET

interface BankApiService {

    @GET("bankdata")
    suspend fun getBankDataNetwork(): BankData
}