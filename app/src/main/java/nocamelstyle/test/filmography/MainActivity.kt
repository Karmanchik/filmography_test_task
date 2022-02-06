package nocamelstyle.test.filmography

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import dagger.hilt.android.AndroidEntryPoint
import nocamelstyle.test.filmography.models.Film
import nocamelstyle.test.filmography.ui.list.FilmsFragment
import nocamelstyle.test.filmography.ui.viewer.ViewerFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(findViewById<FrameLayout>(R.id.frame).id, FilmsFragment())
            .commit()
    }

    fun showFilm(film: Film) {
        supportFragmentManager.beginTransaction()
            .add(findViewById<FrameLayout>(R.id.frame).id, ViewerFragment(film))
            .addToBackStack(null)
            .commit()
    }

}