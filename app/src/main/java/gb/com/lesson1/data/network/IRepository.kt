package gb.com.lesson1.data.network

import gb.com.lesson1.domain.model.UserInfo

interface IRepository {
    fun remoteCheckUser(userInfo: UserInfo): Result<String>
    fun remoteRegister(userInfo: UserInfo): Result<String>
}