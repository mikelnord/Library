package gb.com.lesson1.domain.model

data class UserInfo(val username: String, val password: String)

enum class AuthenticationState {
    AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION, SERVERERROR
}

enum class RegisterState {
    REGISTER, UNREGISTER, NOTREGISTER, SERVERERROR
}