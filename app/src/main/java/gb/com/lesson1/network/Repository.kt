package gb.com.lesson1.network

import gb.com.lesson1.model.UserInfo
import java.io.IOException
import java.lang.Exception

class Repository {
    private val userList =
        arrayListOf(UserInfo("Bob", "BobPassword"), UserInfo("Kate", "123"))

    fun remoteCheckUser(userInfo: UserInfo): Result<String> {
        if ((0..10).random() % 5 == 0) {
            return Result.ServerError(IOException("Server error"))
        }
        if (userList.contains(userInfo)) {
            return Result.Success("Success")
        }
        return Result.Error(Exception("User not found or incorrect password"))
    }

    fun remoteRegister(userInfo: UserInfo): Result<String> {
        if ((0..10).random() % 5 == 0) {
            return Result.ServerError(IOException("Server error"))
        }
        try {
            userList.add(userInfo)
        } catch (e: Exception) {
            return Result.Error(Exception("Error adding a user"))
        }
        return Result.Success("Success")
    }
}