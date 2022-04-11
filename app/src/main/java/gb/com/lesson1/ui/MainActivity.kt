package gb.com.lesson1.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import gb.com.lesson1.R
import gb.com.lesson1.app
import gb.com.lesson1.data.presenter.Presenter

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = restorePresenter()
    }

    private fun restorePresenter(): Presenter {
        val presenter = lastCustomNonConfigurationInstance as? Presenter
        return presenter ?: app.presenter
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        return presenter
    }

    companion object{
        lateinit var presenter: Presenter
    }
}