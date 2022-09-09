package net.trexis.shafikexcersie.retrofit.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import net.trexis.shafikexcersie.commons.ApiException
import net.trexis.shafikexcersie.commons.ApiResponse
import net.trexis.shafikexcersie.model.Account
import net.trexis.shafikexcersie.retrofit.AccountService
import javax.inject.Inject

class AccountRepo @Inject constructor(private val accountService: AccountService) {
    suspend fun getAccounts(): Flow<ApiResponse<List<Account>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val responseData = accountService.getAccounts()
                emit(ApiResponse.Success(data = responseData))
            } catch (e: Exception) {
                emit(ApiException.resolveError(e))
            }
        }.flowOn(Dispatchers.IO)
    }
}