package gb.com.lesson1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import gb.com.lesson1.presenter.Presenter

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = restorePresenter()
    }

    private fun restorePresenter(): Presenter {
        val presenter = lastCustomNonConfigurationInstance as? Presenter
        return presenter ?: Presenter()
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        return presenter
    }

    companion object{
        lateinit var presenter: Presenter
    }
}