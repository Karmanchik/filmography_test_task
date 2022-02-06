package nocamelstyle.test.filmography.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import nocamelstyle.test.filmography.databinding.ItemFilmBinding
import nocamelstyle.test.filmography.models.Film

class FilmsAdapter(
    private val onItemClick: (Film) -> Unit
) : PagingDataAdapter<Film, FilmsAdapter.Holder>(FilmsDiffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Holder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(ItemFilmBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        getItem(position)?.let(holder::bind)
    }

    inner class Holder(private val binding: ItemFilmBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Film) = binding.run {

            itemView.setOnClickListener { onItemClick.invoke(item) }
            titleFilm1.text = item.title

            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${item.poster_path}")
                .dontAnimate()
                .into(filmLogo1)
        }
    }

}

object FilmsDiffUtil : DiffUtil.ItemCallback<Film>() {

    override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean =
        oldItem == newItem

}