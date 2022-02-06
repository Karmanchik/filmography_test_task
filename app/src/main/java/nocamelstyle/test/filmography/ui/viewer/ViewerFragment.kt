package nocamelstyle.test.filmography.ui.viewer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import nocamelstyle.test.filmography.core.ViewState
import nocamelstyle.test.filmography.databinding.FragmentFilmViewerBinding
import nocamelstyle.test.filmography.models.Film
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ViewerFragment(private val film: Film) : Fragment() {

    private var binding: FragmentFilmViewerBinding? = null

    @Inject
    lateinit var viewModel: ViewerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilmViewerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(::getLifecycle, ::showDetailFilm)
        viewModel.getFilmInfo(film.id)
    }

    @SuppressLint("SimpleDateFormat")
    private fun showDetailFilm(state: ViewState<Film>) = binding?.run {
        if (state.data != null) {
            nameFilm.text = state.data.title
            descriptionFilm.text = state.data.overview

            val runtime = Date(state.data.runtime.toLong())
            val format = SimpleDateFormat("HH:mm")
            runtimeFilm.text = format.format(runtime).toString()

            var genresFilm = ""
            for (i in state.data.genres.indices) {
                genresFilm += if (i != state.data.genres.size - 1)
                    "${state.data.genres[i].name}, "
                else
                    state.data.genres[i].name
            }

            filmGenres.text = genresFilm

            var companies = ""
            for (i in state.data.production_companies.indices) {
                companies += if (i != state.data.production_companies.size - 1)
                    "${state.data.production_companies[i].name}, "
                else
                    state.data.production_companies[i].name
            }

            filmCompanies.text = companies

            var countries = ""
            for (i in state.data.production_countries.indices) {
                countries += if (i != state.data.production_countries.size - 1)
                    "${state.data.production_countries[i].name}, "
                else
                    state.data.production_countries[i].name
            }

            filmCountries.text = countries

            var languages = ""
            for (i in state.data.spoken_languages.indices) {
                languages += if (i != state.data.spoken_languages.size - 1)
                    "${state.data.spoken_languages[i].name}, "
                else
                    state.data.spoken_languages[i].name
            }

            filmLanguages.text = languages

            Glide.with(requireContext())
                .load("https://image.tmdb.org/t/p/w500${state.data.poster_path}")
                .into(filmLogo)
        }
    }

}