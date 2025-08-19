package com.altankoc.bankapp.ui.view.home

import com.altankoc.bankapp.data.model.BankDataItem

data class HomeScreenState(
    val isLoading: Boolean = false,
    val bankData: ArrayList<BankDataItem>? = null,
    val errorMessage: String? = null
)