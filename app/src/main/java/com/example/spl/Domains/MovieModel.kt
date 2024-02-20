package com.example.spl.Domains

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.json.JSONArray

data class MovieModel ( val country: String,
                        val genres: List<String>,
                        val id: Int,
                        val images: List<String>,
                        val imdb_rating: String,
                        val poster: String,
                        val title: String,
                        val year: String
)
