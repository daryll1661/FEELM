package com.example.spl.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.spl.Activites.DashboardActivity
import com.example.spl.Domains.MovieModel
import com.example.spl.GenresItem
import com.example.spl.R

class FlimListAdapter(val mContext: Context, private val items: List<MovieModel>) :
    RecyclerView.Adapter<FlimListAdapter.ViewHolder>() {
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val itemView = LayoutInflater.from(context).inflate(R.layout.viewholder, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tittleTxt: TextView = itemView.findViewById(R.id.tittleTxt)
        val pic: ImageView = itemView.findViewById(R.id.pic)


        fun bind(currentItem: MovieModel) {

            tittleTxt.text = currentItem.title
            val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(30))
            Glide.with(itemView.context)
                .load(currentItem.poster)
                .apply(requestOptions)
                .into(pic)
            itemView.setOnClickListener { v: View? ->
               if (context is DashboardActivity)
                   (context as DashboardActivity).onMovieClick(currentItem)
            }
        }
    }
}

interface MovieOnclickListener {
    fun onMovieClick(currentItem: MovieModel)
}
