//package com.example.spl.Activites;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.util.Log;
//import android.view.View;
//import android.widget.ProgressBar;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.viewpager2.widget.CompositePageTransformer;
//import androidx.viewpager2.widget.MarginPageTransformer;
//import androidx.viewpager2.widget.ViewPager2;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.example.spl.Adapters.CategoryListAdapter;
//import com.example.spl.Adapters.FlimListAdapter;
//import com.example.spl.Adapters.MovieOnclickListener;
//import com.example.spl.Adapters.SliderAdapter;
//import com.example.spl.Domains.ListFilm;
//import com.example.spl.Domains.MovieData;
//import com.example.spl.Domains.MovieModel;
//import com.example.spl.Domains.SliderItems;
//import com.example.spl.GenresItem;
//import com.example.spl.R;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import java.util.ArrayList;
//
//public class DashboardActivity extends AppCompatActivity implements MovieOnclickListener {
//    private final String TAG = this.getClass().getSimpleName();
//    private RecyclerView.Adapter<?> adapterBestMovies;
//    private RecyclerView.Adapter<?> adapterUpComing;
//    private RecyclerView.Adapter<?> adapterCategory;
//    private RecyclerView recyclerViewBestMovies;
//    private RecyclerView recyclerViewUpcoming;
//    private RecyclerView recyclerViewCategory;
//    private RequestQueue mRequestQueue;
//    private StringRequest mStringRequest;
//    private StringRequest mStringRequest2;
//    private StringRequest mStringRequest3;
//    private ProgressBar loading1;
//    private ProgressBar loading2;
//    private ProgressBar loading3;
//    private ViewPager2 viewPager2;
//    private ArrayList<MovieModel> upcomingMoviesList;
//    private final Handler slideHandler = new Handler();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_dashboard);
//        initView();
//        banners();
//        sendRequestBestMovies();
//        sendRequestUpComing();
//        sendRequestCategory();
//        upcomingMoviesList = new ArrayList<>();
//        adapterUpComing = new FlimListAdapter(DashboardActivity.this, upcomingMoviesList);
//        recyclerViewUpcoming.setAdapter(adapterUpComing);
//    }
//
//    private void sendRequestBestMovies() {
//        mRequestQueue = Volley.newRequestQueue(this);
//        loading1.setVisibility(View.VISIBLE);
//        mStringRequest = new StringRequest(
//                Request.Method.GET,
//                "https://moviesapi.ir/api/v1/movies?page=1",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Gson gson = new Gson();
//                        loading1.setVisibility(View.GONE);
//                        MovieData items = gson.fromJson(response, MovieData.class);
//                        if (items.getData() != null) {
//                            Log.d(TAG, "sendRequest movieList: " + items.getData());
//                            adapterBestMovies = new FlimListAdapter(DashboardActivity.this, items.getData());
//                            recyclerViewBestMovies.setAdapter(adapterBestMovies);
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(com.android.volley.error.VolleyError error) {
//                        loading1.setVisibility(View.GONE);
//                        Log.i("UiLover", "onErrorResponse: " + error.toString());
//                    }
//                });
//        mRequestQueue.add(mStringRequest);
//    }
//
//    private void sendRequestUpComing() {
//        mRequestQueue = Volley.newRequestQueue(this);
//        loading3.setVisibility(View.VISIBLE);
//        mStringRequest3 = new StringRequest(
//                Request.Method.GET,
//                "https://moviesapi.ir/api/v1/movies?page=2",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Gson gson = new Gson();
//                        loading3.setVisibility(View.GONE);
//                        MovieData items = gson.fromJson(response, MovieData.class);
//                        adapterUpComing= new FlimListAdapter(items);
//                        recyclerViewUpcoming.setAdapter(adapterCategory);
//                    }, error -> {
//                        loading2.setVisibility(View.GONE);
//                        Log.i("UiLover","OnErrorResponse:"+ error.toString());
//
//                    });
//         mRequestQueue.add(mStringRequest2);
//                }
//
//    private void sendRequestCategor() {
//        mRequestQueue = Volley.newRequestQueue(this);
//        loading2.setVisibility(View.VISIBLE);
//        mStringRequest2 = new StringRequest(Request.Method.GET,
//                "https://moviesapi.ir/api/v1/movies?page=2",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Gson gson = new Gson();
//                        loading2.setVisibility(View.GONE);
//                        ArrayList<GenresItem> catList = gson.fromJson(response, new TypeToken<ArrayList<GenresItem>>() {
//                        }.getType());
//                        adapterCategory = new CategoryListAdapter(catList);
//                        recyclerViewCategory.setAdapter(adapterCategory);
//                    }, error -> {
//                        loading2.setVisibility(View.GONE);
//                        Log.i("UiLover","OnErrorResponse:"+ error.toString());
//
//                    });
//         mRequestQueue.add(mStringRequest2);
//                }
//
//
//
//
//
//
//
//    }
//
//        private void banners() {
//        ArrayList<SliderItems> sliderItems = new ArrayList<>();
//        sliderItems.add(new SliderItems(R.drawable.wide));
//        sliderItems.add(new SliderItems(R.drawable.wide1));
//        sliderItems.add(new SliderItems(R.drawable.wide3));
//        viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));
//        viewPager2.setClipToPadding(false);
//        viewPager2.setOffscreenPageLimit(3);
//        ((RecyclerView) viewPager2.getChildAt(0)).setOverScrollMode(RecyclerView.OVER_SCROLL_ALWAYS);
//        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
//        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
//        compositePageTransformer.addTransformer((page, position) -> {
//            float r = 1 - Math.abs(position);
//            page.setScaleY(0.85f + r * 0.15f);
//        });
//        viewPager2.setPageTransformer(compositePageTransformer);
//        viewPager2.setCurrentItem(1);
//        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                slideHandler.removeCallbacks(sliderRunnable);
//            }
//        });
//    }
//
//    private final Runnable sliderRunnable = new Runnable() {
//        @Override
//        public void run() {
//            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
//        }
//    };
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        slideHandler.removeCallbacks(sliderRunnable);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        slideHandler.postDelayed(sliderRunnable, 2000);
//    }
//
//    private void initView() {
//        viewPager2 = findViewById(R.id.viewpagerSlider);
//        recyclerViewBestMovies = findViewById(R.id.view1);
//        recyclerViewBestMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        recyclerViewUpcoming = findViewById(R.id.view3);
//        recyclerViewUpcoming.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        recyclerViewCategory = findViewById(R.id.view2);
//        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        loading1 = findViewById(R.id.progressBar1);
//        loading2 = findViewById(R.id.progressBar2);
//        loading3 = findViewById(R.id.progressBar3);
//    }
//
//    @Override
//    public void onMovieClick(MovieModel currentItem) {
//        Log.d(TAG, "onMovieClick: currentItem: " + currentItem);
//        Intent intent = new Intent(this, YourActivity.class);
//        startActivity(intent);
//    }
//}