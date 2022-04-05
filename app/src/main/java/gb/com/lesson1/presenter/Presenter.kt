package gb.com.lesson1.presenter

import androidx.lifecycle.MutableLiveData
import gb.com.lesson1.model.AuthenticationState
import gb.com.lesson1.model.UserInfo
import gb.com.lesson1.network.Repository
import gb.com.lesson1.network.Result
import kotlinx.coroutines.*

class Presenter {

    var currentUser: UserInfo? = null
    private val repository = Repository()

    var authenticationState: MutableLiveData<AuthenticationState> =
        MutableLiveData(AuthenticationState.UNAUTHENTICATED)

    private val userList =
        arrayListOf(UserInfo("Bob", "BobPassword"), UserInfo("Kate", "123"))

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

    fun onRegister(userInfo: UserInfo) {
        userList.add(userInfo)
    }

    fun onLogout() {
        currentUser = null
        authenticationState.value = AuthenticationState.UNAUTHENTICATED
    }

}