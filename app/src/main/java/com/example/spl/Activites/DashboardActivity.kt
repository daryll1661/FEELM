package com.example.spl.Activites

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.spl.Adapters.CategoryListAdapter
import com.example.spl.Adapters.FlimListAdapter
import com.example.spl.Adapters.MovieOnclickListener
import com.example.spl.Adapters.SliderAdapter
import com.example.spl.Domains.ListFilm
import com.example.spl.Domains.MovieData
import com.example.spl.Domains.MovieModel
import com.example.spl.Domains.SliderItems
import com.example.spl.GenresItem
import com.example.spl.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DashboardActivity : AppCompatActivity(), MovieOnclickListener {
    private val TAG: String = this.javaClass.simpleName
    private lateinit var adapterBestMovies: RecyclerView.Adapter<*>
    private lateinit var adapterUpComing: RecyclerView.Adapter<*>
    private lateinit var adapterCategory: RecyclerView.Adapter<*>
    private lateinit var recyclerViewBestMovies: RecyclerView
    private lateinit var recyclerViewUpcoming: RecyclerView
    private lateinit var recyclerViewCategory: RecyclerView
    private lateinit var mRequestQueue: RequestQueue
    private lateinit var mStringRequest: StringRequest
    private lateinit var mStringRequest2: StringRequest
    private lateinit var mStringRequest3: StringRequest
    private lateinit var loading1: ProgressBar
    private lateinit var loading2: ProgressBar
    private lateinit var loading3: ProgressBar
    private lateinit var viewPager2: ViewPager2

    private lateinit var upcomingMoviesList: ArrayList<MovieModel>
    private lateinit var categories: ArrayList<GenresItem>

    private val slideHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        initView()
        banners()
        sendRequestBestMovies()
        sendRequestUpComing()
        sendRequestCategory()
        upcomingMoviesList = arrayListOf()
        adapterUpComing = FlimListAdapter(this@DashboardActivity, upcomingMoviesList)
        recyclerViewUpcoming.adapter = adapterUpComing

        categories = arrayListOf()
        adapterCategory = CategoryListAdapter(this, categories)
        recyclerViewCategory.adapter = adapterCategory
    }

    private fun sendRequestBestMovies() {
        mRequestQueue = Volley.newRequestQueue(this)
        loading1.visibility = View.VISIBLE
        mStringRequest = StringRequest(
            Request.Method.GET,
            "https://moviesapi.ir/api/v1/movies?page=1",
            Response.Listener { response ->

                val gson = Gson()
                loading1.visibility = View.GONE
                val items = gson.fromJson(response, MovieData::class.java)
                items.data?.let {
                    Log.d(TAG, "sendRequest movieList: $it")
                    adapterBestMovies = FlimListAdapter(this@DashboardActivity, it)
                    recyclerViewBestMovies.adapter = adapterBestMovies
                }

            },
            Response.ErrorListener { error ->
                loading1.visibility = View.GONE
                Log.i("UiLover", "onErrorResponse: " + error.toString())
            })
        mRequestQueue.add(mStringRequest)
    }

    private fun sendRequestUpComing() {
        mRequestQueue = Volley.newRequestQueue(this)
        loading3.visibility = View.VISIBLE
        mStringRequest3 = StringRequest(
            Request.Method.GET,
            "https://moviesapi.ir/api/v1/movies?page=2",
            Response.Listener { response ->

                val gson = Gson()
                loading3.visibility = View.GONE
                val items = gson.fromJson(response, MovieData::class.java)
                items.data?.let {
                    Log.d(TAG, "sendRequest upcoming moviel list: $it")
                    upcomingMoviesList.addAll(it)
                }

                adapterUpComing.notifyDataSetChanged()
            },
            Response.ErrorListener { error ->
                loading3.visibility = View.GONE
                Log.i("UiLover", "onErrorResponse: " + error.toString())
            })
        mRequestQueue.add(mStringRequest3)
    }


    private fun sendRequestCategory() {
        mRequestQueue = Volley.newRequestQueue(this)
        loading2.visibility = View.VISIBLE
        mStringRequest2 = StringRequest(
            Request.Method.GET,
            "https://moviesapi.ir/api/v1/genres",
            Response.Listener { response ->

                val gson = Gson()
                loading2.visibility = View.GONE
                val items: List<GenresItem> = gson.fromJson(response, object : TypeToken<List<GenresItem>>() {}.type)
                categories.addAll(items)

                adapterCategory.notifyDataSetChanged()
            },
            Response.ErrorListener { error ->
                loading2.visibility = View.GONE
                Log.i("UiLover", "onErrorResponse: " + error.toString())
            })
        mRequestQueue.add(mStringRequest2)
    }

    private fun banners() {
        val sliderItems = ArrayList<SliderItems>()
        sliderItems.add(SliderItems(R.drawable.wide))
        sliderItems.add(SliderItems(R.drawable.wide1))
        sliderItems.add(SliderItems(R.drawable.wide3))
        viewPager2.adapter = SliderAdapter(sliderItems, viewPager2)
        viewPager2.clipToPadding = false
        viewPager2.offscreenPageLimit = 3
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_ALWAYS
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - Math.abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }
        viewPager2.setPageTransformer(compositePageTransformer)
        viewPager2.currentItem = 1
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                slideHandler.removeCallbacks(sliderRunnable)
            }
        })
    }

    private val sliderRunnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    override fun onPause() {
        super.onPause()
        slideHandler.removeCallbacks(sliderRunnable)
    }

    override fun onResume() {
        super.onResume()
        slideHandler.postDelayed(sliderRunnable, 2000)
    }

    private fun initView() {
        viewPager2 = findViewById(R.id.viewpagerSlider)
        recyclerViewBestMovies = findViewById(R.id.view1)
        recyclerViewBestMovies.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewUpcoming = findViewById(R.id.view3)
        recyclerViewUpcoming.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCategory = findViewById(R.id.view2)
        recyclerViewCategory.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        loading1 = findViewById(R.id.progressBar1)
        loading2 = findViewById(R.id.progressBar2)
        loading3 = findViewById(R.id.progressBar3)
    }

    override fun onMovieClick(currentItem: MovieModel) {
        Log.d(TAG, "onMovieClick: currentItem: $currentItem")
        intent
    }
}