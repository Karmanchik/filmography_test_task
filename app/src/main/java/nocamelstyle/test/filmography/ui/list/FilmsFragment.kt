package nocamelstyle.test.filmography.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import nocamelstyle.test.filmography.MainActivity
import nocamelstyle.test.filmography.databinding.FragmentFilmsListBinding
import javax.inject.Inject

@AndroidEntryPoint
class FilmsFragment : Fragment() {

    private val filmsAdapter by lazy(LazyThreadSafetyMode.PUBLICATION) {
        FilmsAdapter {
            (requireActivity() as MainActivity).showFilm(it)
        }
    }

    private var binding: FragmentFilmsListBinding? = null

    @Inject
    lateinit var viewModel: FilmsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilmsListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.filmsSource.observe(::getLifecycle) { filmsAdapter.submitData(lifecycle, it) }

        binding?.run {
            search.doOnTextChanged { text, _, _, _ ->
                viewModel.search = text.toString()
                filmsAdapter.refresh()
            }

            rvFilms.adapter = filmsAdapter
            rvFilms.layoutManager = GridLayoutManager(requireContext(), 3)
        }
    }

}