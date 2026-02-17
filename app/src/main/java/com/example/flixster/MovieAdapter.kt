package com.example.flixster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieAdapter(private val movies: List<Movie>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount() = movies.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val overviewTextView: TextView = itemView.findViewById(R.id.overviewTextView)
        private val posterImageView: ImageView = itemView.findViewById(R.id.posterImageView)

        fun bind(movie: Movie) {
            titleTextView.text = movie.title
            overviewTextView.text = movie.overview
            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500/${movie.posterPath}")
                .into(posterImageView)
        }
    }
}