package net.trexis.shafikexcersie.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Transactions(
    @field:SerializedName("id")
    val id: String? = null,
    @field:SerializedName("title")
    val title: String? = null,
    @field:SerializedName("balance")
    val balance: Double? = null,
)
