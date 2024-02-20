package com.example.spl.Activites

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.FlimItems
import com.example.spl.Adapters.ActorsListAdapter
import com.example.spl.Adapters.CategoryEachFlimListAdapter
import com.example.spl.R
import com.google.gson.Gson

class DetailActivity : AppCompatActivity() {
    private var mRequestQueue: RequestQueue? = null
    private var mStringRequest: StringRequest? = null
    private var progressBar: ProgressBar? = null
    private var tittleTxt: TextView? = null
    private var movieRateTxt: TextView? = null
    private var movieTimeTxt: TextView? = null
    private var movieSummaryInfo: TextView? = null
    private var movieActorsInfo: TextView? = null
    private var idFlim = 0
    private var pic2: ImageView? = null
    private var backImg: ImageView? = null
    private var adapterActorList: RecyclerView.Adapter<*>? = null
    private var adapterCategory: RecyclerView.Adapter<*>? = null
    private var recyclerViewActor: RecyclerView? = null
    private var recyclerViewCategory: RecyclerView? = null
    private var scrollView: NestedScrollView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        idFlim = intent.getIntExtra("id", 0)
        initview()
        sendRequest()
        recyclerViewActor?.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerViewCategory?.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        backImg?.setOnClickListener(View.OnClickListener { v: View? ->
            finish()
        })
    }

    private fun sendRequest() {
        mRequestQueue = Volley.newRequestQueue(this)
        progressBar!!.visibility = View.GONE
        scrollView!!.visibility = View.VISIBLE
        mStringRequest = StringRequest(
            Request.Method.GET,
            "https://moviesapi.ir/api/v1/movies/$idFlim", { response: String? ->
                val gson = Gson()
                progressBar!!.visibility = View.GONE
                scrollView!!.visibility = View.VISIBLE
                val items = gson.fromJson(response, FlimItems::class.java)
                Glide.with(this@DetailActivity)
                    .load(items.poster)
                    .into(pic2!!)
                tittleTxt!!.text = items.title
                movieRateTxt!!.text = items.imdbRating
                movieTimeTxt!!.text = items.plot
                movieActorsInfo!!.text = items.actors
                if (items.images != null) {
                    adapterActorList = ActorsListAdapter(items.images)
                    recyclerViewActor!!.adapter = adapterActorList
                }
                if (items.genres != null) {
                    adapterCategory = CategoryEachFlimListAdapter(items.genres)
                    recyclerViewCategory!!.adapter = adapterCategory
                }
            }) { error: VolleyError? -> progressBar!!.visibility = View.GONE }
        mRequestQueue!!.add(mStringRequest)
    }

    private fun initview() {
        tittleTxt = findViewById(R.id.movieNameTxt)
        progressBar = findViewById(R.id.progressBarDetail)
        scrollView = findViewById(R.id.scrollView2)
        pic2 = findViewById(R.id.picDetail)
        movieRateTxt = findViewById(R.id.movieStar)
        movieTimeTxt = findViewById(R.id.movieTime)
        movieSummaryInfo = findViewById(R.id.movieSummary)
        movieActorsInfo = findViewById(R.id.movieActorInfo)
        backImg = findViewById(R.id.backImg)
        recyclerViewCategory = findViewById(R.id.genreView)
        recyclerViewActor = findViewById(R.id.imageRecycler)
    }
}