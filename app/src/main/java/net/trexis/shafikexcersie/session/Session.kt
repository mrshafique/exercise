package net.trexis.shafikexcersie.session

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import javax.inject.Inject

class Session @Inject constructor(context: Context) {
    var sp: SharedPreferences
    var spEdit: SharedPreferences.Editor

    init {
        val masterKey: MasterKey =
            MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

        sp = EncryptedSharedPreferences.create(
            context,
            "trexis",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        spEdit = sp.edit()
    }
}