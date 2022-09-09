package net.trexis.shafikexcersie.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.trexis.shafikexcersie.retrofit.AccountService
import net.trexis.shafikexcersie.retrofit.LoginService
import net.trexis.shafikexcersie.retrofit.MyInterceptor
import net.trexis.shafikexcersie.retrofit.TransactionService
import net.trexis.shafikexcersie.utils.Constants
import net.trexis.shafikexcersie.utils.Constants.Companion.timeOutInSec
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DiRetrofit {
    @Provides
    @Singleton
    fun providesSessionRepo(): MyInterceptor {
        return MyInterceptor()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(myInterceptor: MyInterceptor): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(myInterceptor)
            .readTimeout(timeOutInSec, TimeUnit.SECONDS)
            .connectTimeout(timeOutInSec, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun providesScalarsConverterFactory(): ScalarsConverterFactory {
        return ScalarsConverterFactory.create()
    }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun providesRetrofitInstance(
        okHttpClient: OkHttpClient,
        scalarsConverterFactory: ScalarsConverterFactory,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(scalarsConverterFactory)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun providesLoginService(retrofit: Retrofit): LoginService =
        retrofit.create(LoginService::class.java)

    @Provides
    @Singleton
    fun providesStudentService(retrofit: Retrofit): AccountService =
        retrofit.create(AccountService::class.java)

    @Provides
    @Singleton
    fun providesTransactionService(retrofit: Retrofit): TransactionService =
        retrofit.create(TransactionService::class.java)
}