package gb.com.lesson1.model

data class UserInfo(val username:String,val password:String)

enum class AuthenticationState {
    AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
}