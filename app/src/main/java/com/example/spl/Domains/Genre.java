
package com.example.project155.domains;

import com.example.spl.Domains.ListFilm;
import com.example.spl.GenresItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Genre {

    private List<GenresItem> genres;

    public List<GenresItem> getGenres() {
        return genres;
    }

    public void setGenres(List<GenresItem> genres) {
        this.genres = genres;
    }
}
