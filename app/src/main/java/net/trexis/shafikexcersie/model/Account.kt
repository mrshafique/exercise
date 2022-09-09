package net.trexis.shafikexcersie.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Account(
    @field:SerializedName("id")
    val id: String? = null,
    @field:SerializedName("name")
    val name: String? = null,
    @field:SerializedName("balance")
    val balance: Double? = null,
)