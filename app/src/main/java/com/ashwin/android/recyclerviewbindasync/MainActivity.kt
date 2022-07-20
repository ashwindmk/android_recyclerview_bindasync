package com.ashwin.android.recyclerviewbindasync

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val movieRecyclerView = findViewById<RecyclerView>(R.id.movies_recycler_view)
        val movieAdapter = MovieAdapter(this, DataProvider.getMovies())
        movieRecyclerView.layoutManager = LinearLayoutManager(this)
        movieRecyclerView.adapter = movieAdapter
    }
}
