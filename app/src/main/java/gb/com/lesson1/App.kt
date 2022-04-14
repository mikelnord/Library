package gb.com.lesson1

import android.app.Application
import android.content.Context
import gb.com.lesson1.data.network.MockRepository
import gb.com.lesson1.domain.IRepository

class App : Application() {
    val repository: IRepository by lazy { MockRepository() }

}

val Context.app: App
    get() {
        return applicationContext as App
    }