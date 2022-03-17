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
import com.example.pinterest.Networking.ResponseItem
import com.example.pinterest.R
import com.google.android.material.imageview.ShapeableImageView

class HomeTwoAdapter(var items:ArrayList<ResponseItem>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var clickitemhome:((word:String)-> Unit) ? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_home,parent,false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val home = items[position]

        if (holder is HomeViewHolder) {
            val tv_title = holder.tv_title
            val iv_photo = holder.iv_photo
            val item = holder.item

            item.setOnClickListener {
                clickitemhome?.invoke(home.description.toString())
            }

            if (home.description == null){
                tv_title.text = home.altDescription
            }else{
                tv_title.text = home.description
            }

            Glide.with(holder.itemView.context)
                .load(home.urls?.small)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.pinterest)
                .placeholder(R.drawable.pinterest)
                .into(iv_photo)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("CutPasteId")
    class HomeViewHolder(view: View):RecyclerView.ViewHolder(view){
        var tv_title: TextView
        var iv_photo:ShapeableImageView
        var item:FrameLayout

        init {
            tv_title = view.findViewById(R.id.textView)
            iv_photo = view.findViewById(R.id.imageView)
            item = view.findViewById(R.id.item_home)
        }
    }
}