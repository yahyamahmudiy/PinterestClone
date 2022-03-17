package com.example.pinterest.Adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pinterest.Model.Photos
import com.example.pinterest.R


class PhotosAdapter(val lists: ArrayList<Photos>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var photoItemClick:((photo: Photos)->Unit)?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PhotoListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout_home, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val listItem = lists[position]
        if (holder is PhotoListViewHolder) {
            holder.apply {
                tv_describtion.text = listItem.description
                Glide.with(iv_home.context).load(listItem.url)
                    .error(R.mipmap.ic_launcher)
                    .placeholder(ColorDrawable(Color.parseColor(listItem.color)))
                    .into(iv_home)


                iv_home.setOnClickListener {
                    photoItemClick!!.invoke(listItem)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    class PhotoListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tv_describtion = view.findViewById<TextView>(R.id.textView)
        var iv_home = view.findViewById<ImageView>(R.id.imageView)

    }

}