package com.example.spl.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spl.GenresItem
import com.example.spl.R

class CategoryEachFlimListAdapter(private val items: MutableList<String>) :
    RecyclerView.Adapter<CategoryEachFlimListAdapter.ViewHolder>() {
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryEachFlimListAdapter.ViewHolder {
        context = parent.context
        val itemView = LayoutInflater.from(context).inflate(R.layout.viewholder_category, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]

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


