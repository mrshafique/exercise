package net.trexis.shafikexcersie.retrofit.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import net.trexis.shafikexcersie.commons.ApiException
import net.trexis.shafikexcersie.commons.ApiResponse
import net.trexis.shafikexcersie.retrofit.LoginService
import javax.inject.Inject

class LoginRepo @Inject constructor(private val loginService: LoginService) {

    suspend fun loginUser(
        strUsername: String,
        strPassword: String
    ): Flow<ApiResponse<Int>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val responseData =
                    loginService.loginUser(username = strUsername, password = strPassword)
                emit(ApiResponse.Success(data = responseData.code()))
            } catch (e: Exception) {
                Log.e("loginRepo", "catch: ${e.message}")
                emit(ApiException.resolveError(e))
            }
        }.flowOn(Dispatchers.IO)
    }

}