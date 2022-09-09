package net.trexis.shafikexcersie.retrofit

import net.trexis.shafikexcersie.model.Account
import retrofit2.http.GET

interface AccountService {
    //http://localhost:5555/accounts
    @GET("accounts")
    suspend fun getAccounts(): List<Account>
}