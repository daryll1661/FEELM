package com.example.spl.Domains

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ListFilm {
    operator fun get(position: Int) {

    }

    @JvmField
    @SerializedName("data")
    @Expose
    var data: List<MovieModel>? = null

    @SerializedName("metadata")
    @Expose
    var metadata: Metadata? = null
}
