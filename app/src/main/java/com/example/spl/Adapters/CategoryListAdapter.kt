package com.example.spl.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spl.Domains.MovieModel
import com.example.spl.GenresItem
import com.example.spl.R

class CategoryListAdapter(val mContext: Context, private val items: List<GenresItem>) :
    RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListAdapter.ViewHolder {
        context = parent.context
        val itemView = LayoutInflater.from(context).inflate(R.layout.viewholder_category, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryListAdapter.ViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tittleTxt: TextView = itemView.findViewById(R.id.TittleTxt)



        fun bind(currentItem: GenresItem) {

            tittleTxt.text = currentItem.name

            itemView.setOnClickListener { v: View? ->

            }
        }
    }
}

interface MovieOnclickListener1 {
    fun onMovieClick(currentItem: MovieModel)
}
