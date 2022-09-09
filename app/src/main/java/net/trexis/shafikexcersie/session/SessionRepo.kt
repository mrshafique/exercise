package net.trexis.shafikexcersie.session

import javax.inject.Inject

class SessionRepo @Inject constructor(private val session: Session) {
    fun isLogin(isLogin: Boolean) { session.spEdit.putBoolean("a", isLogin).apply() }
    fun isLogin(): Boolean { return session.sp.getBoolean("a", false) }
}