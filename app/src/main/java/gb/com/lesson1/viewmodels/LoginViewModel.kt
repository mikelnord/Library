package gb.com.lesson1.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gb.com.lesson1.data.AuthenticationState
import gb.com.lesson1.data.RegisterState
import gb.com.lesson1.data.UserInfo
import gb.com.lesson1.data.network.Result
import gb.com.lesson1.domain.IRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: IRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    var currentUser: UserInfo? = null

    var authenticationState: MutableLiveData<AuthenticationState> =
        MutableLiveData(AuthenticationState.UNAUTHENTICATED)
    var registerState: MutableLiveData<RegisterState> = MutableLiveData(RegisterState.UNREGISTER)
    var progressState: MutableLiveData<Boolean> = MutableLiveData(false)

    fun onLogin(userInfo: UserInfo) {
        viewModelScope.launch(dispatcher) {
            showProgress()
            delay(3000)
            return@launch when (repository.remoteCheckUser(userInfo)) {
                is Result.Error -> {
                    hideProgress()
                    authenticationState.postValue(AuthenticationState.INVALID_AUTHENTICATION)
                }
                is Result.Success -> {
                    currentUser = userInfo
                    hideProgress()
                    authenticationState.postValue(AuthenticationState.AUTHENTICATED)
                }
                is Result.ServerError -> {
                    hideProgress()
                    authenticationState.postValue(AuthenticationState.SERVERERROR)
                }
            }
        }
    }

    fun onRegister(userInfo: UserInfo) {
        viewModelScope.launch(dispatcher) {
            showProgress()
            delay(3000)
            hideProgress()
            return@launch when (repository.remoteRegister(userInfo)) {
                is Result.Error -> registerState.postValue(RegisterState.NOTREGISTER)
                is Result.ServerError -> registerState.postValue(RegisterState.SERVERERROR)
                is Result.Success -> registerState.postValue(RegisterState.REGISTER)
            }
        }
    }

    fun resetRegisterState() {
        registerState.value = RegisterState.UNREGISTER
    }

    fun resetAuthenticationState() {
        authenticationState.value = AuthenticationState.UNAUTHENTICATED
    }

    private fun showProgress() {
        progressState.postValue(true)
    }

    private fun hideProgress() {
        progressState.postValue(false)
    }


    fun onLogout() {
        currentUser = null
        authenticationState.value = AuthenticationState.UNAUTHENTICATED
    }

}