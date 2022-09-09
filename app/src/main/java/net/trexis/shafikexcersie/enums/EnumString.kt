package net.trexis.shafikexcersie.enums

enum class EnumString(private var strValue: String) {
    AUTH_ERROR("Authentication error"), ;

    fun getValue(): String {
        return strValue
    }
}