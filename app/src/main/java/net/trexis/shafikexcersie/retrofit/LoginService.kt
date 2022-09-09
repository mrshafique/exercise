package net.trexis.shafikexcersie.retrofit

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService {
    //http://localhost:5555/login
    @FormUrlEncoded
    @POST("login")
    suspend fun loginUser(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<String>
}