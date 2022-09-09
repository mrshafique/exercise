package net.trexis.shafikexcersie.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import net.trexis.shafikexcersie.commons.ApiResponse
import net.trexis.shafikexcersie.retrofit.repository.LoginRepo
import net.trexis.shafikexcersie.session.SessionRepo
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepo: LoginRepo,
    private val sessionRepo: SessionRepo
) : ViewModel() {

    val stateFlowLoginResp =
        MutableStateFlow<ApiResponse<Int>>(ApiResponse.Empty)

    fun getLogin(
        strUsername: String,
        strPassword: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            loginRepo.loginUser(strUsername = strUsername, strPassword = strPassword)
                .collectLatest {
                    stateFlowLoginResp.emit(it)
                }
        }
    }
}