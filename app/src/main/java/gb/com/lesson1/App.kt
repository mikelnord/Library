package gb.com.lesson1

import android.app.Application
import android.content.Context
import gb.com.lesson1.data.network.IRepository
import gb.com.lesson1.data.network.MockRepository
import gb.com.lesson1.data.presenter.Presenter

class App : Application() {
    private val repository: IRepository by lazy { MockRepository() }
    val presenter: Presenter by lazy { Presenter(repository) }
}

val Context.app: App
    get() {
        return applicationContext as App
    }