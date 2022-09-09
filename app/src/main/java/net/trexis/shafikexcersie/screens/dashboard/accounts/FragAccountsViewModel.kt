package net.trexis.shafikexcersie.screens.dashboard.accounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import net.trexis.shafikexcersie.commons.ApiResponse
import net.trexis.shafikexcersie.model.Account
import net.trexis.shafikexcersie.retrofit.repository.AccountRepo
import javax.inject.Inject

@HiltViewModel
class FragAccountsViewModel @Inject constructor(
    private val accountRepo: AccountRepo
) : ViewModel() {

    val stateFlowAccounts =
        MutableStateFlow<ApiResponse<List<Account>>>(ApiResponse.Empty)

    fun getAccounts() {
        viewModelScope.launch(Dispatchers.IO) {
            accountRepo.getAccounts()
                .collectLatest {
                    stateFlowAccounts.emit(it)
                }
        }
    }
}