package net.trexis.shafikexcersie.screens.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import net.trexis.shafikexcersie.commons.ApiResponse
import net.trexis.shafikexcersie.model.Transactions
import net.trexis.shafikexcersie.retrofit.repository.TransactionRepo
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val transactionRepo: TransactionRepo
) : ViewModel() {

    val stateFlowTransactions =
        MutableStateFlow<ApiResponse<List<Transactions>>>(ApiResponse.Empty)

    fun getTransactionsByAccountId(
        strAccountId: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionRepo.getTransactionsByAccountId(strAccountId = strAccountId)
                .collectLatest {
                    stateFlowTransactions.emit(it)
                }
        }
    }
}