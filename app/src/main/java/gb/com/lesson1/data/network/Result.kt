package gb.com.lesson1.data.network

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    data class ServerError(val exception: Exception) : Result<Nothing>()

}