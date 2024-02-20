package com.example.spl.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spl.Adapters.ActorsListAdapter.Viewholder
import com.example.spl.R

class ActorsListAdapter(var images: List<String>) : RecyclerView.Adapter<Viewholder>() {
    var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        context = parent.context
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_actors, parent, false)
        return Viewholder(inflate)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        Glide.with(context!!)
            .load(images[position])
            .into(holder.pic)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pic: ImageView

        init {
            pic = itemView.findViewById(R.id.itemImage)
        }
    }
}
