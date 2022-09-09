package net.trexis.shafikexcersie.retrofit

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Singleton

@Singleton
class MyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val response = chain.proceed(originalRequest)
        loge("${response.code}; [${response.request.method}] ${response.request.url}}")
        return response
    }

    private fun loge(msg: String) {
        Log.e("interceptor", msg)
    }
}