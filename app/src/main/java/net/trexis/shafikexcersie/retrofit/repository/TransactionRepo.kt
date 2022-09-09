package net.trexis.shafikexcersie.retrofit.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import net.trexis.shafikexcersie.commons.ApiException
import net.trexis.shafikexcersie.commons.ApiResponse
import net.trexis.shafikexcersie.model.Transactions
import net.trexis.shafikexcersie.retrofit.TransactionService
import javax.inject.Inject

class TransactionRepo @Inject constructor(private val transactionService: TransactionService) {
    suspend fun getTransactionsByAccountId(strAccountId: String): Flow<ApiResponse<List<Transactions>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val responseData =
                    transactionService.getTransactionsByAccountId(accountId = strAccountId)
                emit(ApiResponse.Success(data = responseData))
            } catch (e: Exception) {
                emit(ApiException.resolveError(e))
            }
        }.flowOn(Dispatchers.IO)
    }
}