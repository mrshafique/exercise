package net.trexis.shafikexcersie.commons

import android.util.Log
import net.trexis.shafikexcersie.enums.EnumString
import org.json.JSONObject
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ApiException {
    companion object {
        fun resolveError(e: Exception): ApiResponse.Error {
            var error = e
            when (e) {
                is SocketTimeoutException ->
                    error = NetworkErrorException(errorMessage = "Connection error!")
                is ConnectException ->
                    error = NetworkErrorException(errorMessage = "No internet access!")
                is UnknownHostException ->
                    error = NetworkErrorException(errorMessage = "No internet access!")
                is HttpException -> {
                    Log.e("ApiException", "code: ${e.code()}")
                    error = when (e.code()) {
                        502 -> NetworkErrorException(e.code(), "Internal error!")
                        401 -> NetworkErrorException(
                            e.code(),
                            EnumString.AUTH_ERROR.getValue()
                        )
//"Authentication error")
                        else -> NetworkErrorException.parseException(e) //400
                    }
                }
            }
            return ApiResponse.Error(error)
        }
    }

    open class NetworkErrorException(
        val errorCode: Int = -1,
        val errorMessage: String,
        val response: String = ""
    ) : Exception() {
        override val message: String
            get() = localizedMessage

        override fun getLocalizedMessage(): String {
            return errorMessage
        }

        companion object {
            fun parseException(e: HttpException): NetworkErrorException {
                val errorBody = e.response()?.errorBody()?.string()

                return try {//here you can parse the error body that comes from server
                    NetworkErrorException(
                        e.code(),
                        JSONObject(errorBody!!).getString("message")
                    )
                } catch (_: Exception) {
                    NetworkErrorException(e.code(), "Unexpected error!!ً")
                }
            }
        }
    }

    class AuthenticationException(authMessage: String) :
        NetworkErrorException(errorMessage = authMessage)
}