package com.example.pinterest.Fragment.Home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pinterest.Adapter.HomeTwoAdapter
import com.example.pinterest.Helper.SpaceItemDecoration
import com.example.pinterest.Networking.ResponseItem
import com.example.pinterest.Networking.RetrofitHttp
import com.example.pinterest.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragmentForYou : Fragment() {
    private var photos = ArrayList<ResponseItem>()
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.fragment_home_for_you, container, false)

        recyclerView = view.findViewById<View>(R.id.recyclerView) as RecyclerView
        recyclerView?.setLayoutManager(StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL))

        val decoration = SpaceItemDecoration(10)
        recyclerView?.addItemDecoration(decoration)


        apiPosterListRetrofit()

        return view
    }

    fun apiPosterListRetrofit(){
        RetrofitHttp.posterService.listPhotosForYou().enqueue(object :
            Callback<ArrayList<ResponseItem>> {
            override fun onResponse(call: Call<ArrayList<ResponseItem>>, response: Response<ArrayList<ResponseItem>>) {
                photos.clear()
                photos.addAll(response.body()!!)
                //progressBar.visibility = View.GONE
                refreshAdapter(photos)
            }

            override fun onFailure(call: Call<ArrayList<ResponseItem>>, t: Throwable) {
                Log.d("@@@",t.message.toString())
            }

        })
    }

    fun refreshAdapter(photos: ArrayList<ResponseItem>){
        val homeTwoAdapter = HomeTwoAdapter(photos)
        recyclerView?.adapter = homeTwoAdapter
    }
}