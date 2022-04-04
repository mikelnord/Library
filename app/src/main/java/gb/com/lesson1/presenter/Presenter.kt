package gb.com.lesson1.presenter

import androidx.lifecycle.MutableLiveData
import gb.com.lesson1.model.AuthenticationState
import gb.com.lesson1.model.UserInfo
import kotlinx.coroutines.*

class Presenter() {

    var authenticationState: MutableLiveData<AuthenticationState> =
        MutableLiveData(AuthenticationState.UNAUTHENTICATED)

    private val userList =
        arrayListOf<UserInfo>(UserInfo("Bob", "BobPassword"), UserInfo("Kate", "123"))

    suspend fun onLogin(userInfo: UserInfo) {
            delay(3000)
            if (onCheckUser(userInfo)) {
                authenticationState.postValue(AuthenticationState.AUTHENTICATED)
            } else {
                authenticationState.postValue(AuthenticationState.INVALID_AUTHENTICATION)
            }

    }

    fun onRegister(userInfo: UserInfo) {
        userList.add(userInfo)
    }

    private fun onCheckUser(userInfo: UserInfo) = userList.contains(userInfo)

    fun onLogout() {
        authenticationState.value = AuthenticationState.UNAUTHENTICATED
    }

}