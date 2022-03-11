package com.example.pinterest.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pinterest.R
import dev.matyaqubov.pinterest.service.model.SearchResultsItem

class SearchPhotosAdapter(val lists: ArrayList<SearchResultsItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PhotoListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout_home, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = lists[position]
        if (holder is PhotoListViewHolder) {
            holder.apply {
                tv_describtion.text = item.description

                Glide.with(iv_home.context)
                    .load(item.urls!!.thumb)
                    .placeholder(R.drawable.pinterest)
                    .error(R.mipmap.ic_launcher)
                    .into(iv_home)
                //iv_more.setOnClickListener { Toast.makeText(iv_home.context, "Coming soon", Toast.LENGTH_SHORT).show() }
            }
        }
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    class PhotoListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tv_describtion = view.findViewById<TextView>(R.id.textView)
        var iv_home = view.findViewById<ImageView>(R.id.imageView)
        //var iv_more = view.findViewById<ImageView>(R.id.iv_share_more)
    }

}