package gb.com.lesson1.domain

import gb.com.lesson1.data.network.Result
import gb.com.lesson1.data.UserInfo

interface IRepository {
    fun remoteCheckUser(userInfo: UserInfo): Result<String>
    fun remoteRegister(userInfo: UserInfo): Result<String>
}