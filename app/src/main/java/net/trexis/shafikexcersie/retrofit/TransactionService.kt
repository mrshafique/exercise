package net.trexis.shafikexcersie.retrofit

import net.trexis.shafikexcersie.model.Account
import net.trexis.shafikexcersie.model.Transactions
import retrofit2.http.GET
import retrofit2.http.Query

interface TransactionService {
    //http://localhost:5555/transactions?accountId=3
    @GET("transactions")
    suspend fun getTransactionsByAccountId(@Query("accountId") accountId: String): List<Transactions>
}