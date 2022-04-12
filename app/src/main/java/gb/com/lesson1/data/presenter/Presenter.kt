package gb.com.lesson1.data.presenter

import androidx.lifecycle.MutableLiveData
import gb.com.lesson1.domain.IRepository
import gb.com.lesson1.domain.model.AuthenticationState
import gb.com.lesson1.domain.model.RegisterState
import gb.com.lesson1.domain.model.UserInfo
import gb.com.lesson1.data.network.Result
import kotlinx.coroutines.*

class Presenter(private val repository: IRepository) {

    var currentUser: UserInfo? = null

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

    fun resetRegisterState(){
        registerState.value=RegisterState.UNREGISTER
    }

    fun onLogout() {
        currentUser = null
        authenticationState.value = AuthenticationState.UNAUTHENTICATED
    }

}