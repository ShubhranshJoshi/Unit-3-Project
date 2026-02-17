package com.example.flixster

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    private val movies = mutableListOf<Movie>()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        val movieAdapter = MovieAdapter(movies)
        recyclerView.adapter = movieAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val client = AsyncHttpClient()
        client.get("https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                try {
                    val moviesArray = json.jsonObject.getJSONArray("results")
                    val gson = Gson()
                    val movieType = object : TypeToken<List<Movie>>() {}.type
                    val newMovies = gson.fromJson<List<Movie>>(moviesArray.toString(), movieType)
                    movies.addAll(newMovies)
                    movieAdapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    Log.e("MainActivity", "Encountered a JSON exception", e)
                }
            }

            override fun onFailure(statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?) {
                Log.e("MainActivity", "onFailure: $statusCode")
            }
        })
    }
}
