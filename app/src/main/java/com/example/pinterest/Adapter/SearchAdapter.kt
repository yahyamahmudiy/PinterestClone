package com.example.pinterest.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.pinterest.Model.Home
import com.example.pinterest.Networking.ResponseItem
import com.example.pinterest.R
import com.google.android.material.imageview.ShapeableImageView

class SearchAdapter(var items:ArrayList<Home>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var clickitem:((word:String)-> Unit) ? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_search,parent,false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val home = items[position]

        if (holder is SearchViewHolder) {
            val tv_title = holder.tv_title
            val iv_photo = holder.iv_photo

            holder.item.setOnClickListener {
                clickitem!!.invoke(home.title)
            }
            tv_title.text = home.title
            Glide.with(holder.itemView.context)
                .load(home.photo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv_photo)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("CutPasteId")
    class SearchViewHolder(view: View):RecyclerView.ViewHolder(view){
        var tv_title: TextView
        var iv_photo:ShapeableImageView
        var item=view.findViewById<FrameLayout>(R.id.item)

        init {
            tv_title = view.findViewById(R.id.textView)
            iv_photo = view.findViewById(R.id.imageView)
        }
    }
}