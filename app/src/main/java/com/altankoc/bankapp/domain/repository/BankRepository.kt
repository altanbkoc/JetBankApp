package com.altankoc.bankapp.domain.repository

import com.altankoc.bankapp.data.model.BankData
import com.altankoc.bankapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface BankRepository {
    fun getBankData(): Flow<Resource<BankData>>
}