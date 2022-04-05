package gb.com.lesson1.presenter

import androidx.lifecycle.MutableLiveData
import gb.com.lesson1.model.AuthenticationState
import gb.com.lesson1.model.RegisterState
import gb.com.lesson1.model.UserInfo
import gb.com.lesson1.network.Repository
import gb.com.lesson1.network.Result
import kotlinx.coroutines.*

class Presenter {

    var currentUser: UserInfo? = null
    private val repository = Repository()

    var authenticationState: MutableLiveData<AuthenticationState> =
        MutableLiveData(AuthenticationState.UNAUTHENTICATED)
    var registerState: MutableLiveData<RegisterState> = MutableLiveData(RegisterState.UNREGISTER)

    suspend fun onLogin(userInfo: UserInfo) {
        delay(3000)
        return when (repository.remoteCheckUser(userInfo)) {
            is Result.Error -> {
                authenticationState.postValue(AuthenticationState.INVALID_AUTHENTICATION)
            }
            is Result.Success -> {
                currentUser = userInfo
                authenticationState.postValue(AuthenticationState.AUTHENTICATED)
            }
            is Result.ServerError -> authenticationState.postValue(AuthenticationState.SERVERERROR)
        }
    }

    suspend fun onRegister(userInfo: UserInfo) {
        delay(3000)
        return when (repository.remoteRegister(userInfo)) {
            is Result.Error -> registerState.postValue(RegisterState.NOTREGISTER)
            is Result.ServerError -> registerState.postValue(RegisterState.SERVERERROR)
            is Result.Success -> registerState.postValue(RegisterState.REGISTER)
        }

    }

    fun onLogout() {
        currentUser = null
        authenticationState.value = AuthenticationState.UNAUTHENTICATED
    }

}