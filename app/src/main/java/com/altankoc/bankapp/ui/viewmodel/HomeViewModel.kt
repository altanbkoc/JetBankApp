package com.altankoc.bankapp.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altankoc.bankapp.data.model.BankDataItem
import com.altankoc.bankapp.domain.usecase.GetBankDataUseCase
import com.altankoc.bankapp.ui.view.home.HomeScreenState
import com.altankoc.bankapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBankDataUseCase: GetBankDataUseCase
) : ViewModel(){


    private val _homeState = mutableStateOf(HomeScreenState())
    val homeState: State<HomeScreenState> = _homeState

    private val _filteredBankList = mutableStateOf<List<BankDataItem>>(emptyList())
    val filteredBankList: State<List<BankDataItem>> = _filteredBankList

    init {
        getBankList()
    }

    private fun getBankList(){
        viewModelScope.launch {
            getBankDataUseCase.invoke().collect {
                when(it){
                    is Resource.Error -> {
                        _homeState.value = HomeScreenState(isLoading = false,errorMessage = it.message ?: "Error")
                    }

                    is Resource.Loading -> {
                        _homeState.value = HomeScreenState(isLoading = true)
                    }

                    is Resource.Success -> {
                        delay(1000)
                        _homeState.value = HomeScreenState(isLoading = false, bankData = it.data)
                        _filteredBankList.value = it.data ?: emptyList()
                    }
                }
            }
        }
    }

    fun filteredBankList(query: String){
        _filteredBankList.value = homeState.value.bankData?.filter {
            it.bankDistrict?.contains(query, ignoreCase = true) == true || 
                    it.bankCity?.contains(query, ignoreCase = true) == true
        } ?: emptyList()
    }

}