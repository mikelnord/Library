package gb.com.lesson1.data.network

import gb.com.lesson1.domain.model.UserInfo
import java.io.IOException
import java.lang.Exception

class MockRepository : IRepository {
    private val userList =
        arrayListOf(UserInfo("Bob", "BobPassword"), UserInfo("Kate", "123"))

    override fun remoteCheckUser(userInfo: UserInfo): Result<String> {
        if ((0..10).random() % 5 == 0) {
            return Result.ServerError(IOException("Server error"))
        }
        if (userList.contains(userInfo)) {
            return Result.Success("Success")
        }
        return Result.Error(Exception("User not found or incorrect password"))
    }

    override fun remoteRegister(userInfo: UserInfo): Result<String> {
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