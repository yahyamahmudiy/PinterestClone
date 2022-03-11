package com.example.pinterest.Helper

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class SpaceItemDecoration(private val mSpace: Int) : ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = mSpace
        outRect.right = mSpace
        outRect.bottom = mSpace
        if (parent.getChildAdapterPosition(view) == 0){
            outRect.top = mSpace
        }
    }
}
