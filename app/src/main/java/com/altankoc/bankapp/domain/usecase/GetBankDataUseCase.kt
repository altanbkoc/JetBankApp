package com.altankoc.bankapp.domain.usecase

import com.altankoc.bankapp.domain.repository.BankRepository
import javax.inject.Inject

class GetBankDataUseCase @Inject constructor(
    private val bankRepository: BankRepository
) {

    fun invoke() = bankRepository.getBankData()
}