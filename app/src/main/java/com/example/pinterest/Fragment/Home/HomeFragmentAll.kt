package com.example.pinterest.Fragment.Home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.pinterest.Adapter.HomeTwoAdapter
import com.example.pinterest.Helper.EndlessRecyclerViewScrollListener
import com.example.pinterest.Helper.ProgressDialog
import com.example.pinterest.Helper.SpaceItemDecoration
import com.example.pinterest.Networking.ResponseItem
import com.example.pinterest.Networking.RetrofitHttp
import com.example.pinterest.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragmentAll : Fragment() {
    private var photos = ArrayList<ResponseItem>()
    private var recyclerView: RecyclerView? = null
    lateinit var homeTwoAdapter:HomeTwoAdapter
    var page = 0
    private lateinit var manager: StaggeredGridLayoutManager
    lateinit var swipeRefreshLayout:SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view:View = inflater.inflate(R.layout.fragment_home_all, container, false)

        recyclerView = view.findViewById<View>(R.id.recyclerView) as RecyclerView
        manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView!!.layoutManager = manager

        refreshAdapter(photos)
        apiPosterListRetrofit()


        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh2)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
//            apiPosterListRetrofit()
            photos.clear()
            apiPosterListRetrofit()
//            refreshAdapter(photos)
        }

        val decoration = SpaceItemDecoration(10)
        recyclerView?.addItemDecoration(decoration)

        val scrollListener=object : EndlessRecyclerViewScrollListener(manager){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                apiPosterListRetrofit()
            }

        }
        recyclerView!!.addOnScrollListener(scrollListener)

        return view
    }

    fun apiPosterListRetrofit(){
        ProgressDialog.showProgress(requireContext())
        RetrofitHttp.posterService.getPhotos(getPage()).enqueue(object : Callback<ArrayList<ResponseItem>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<ArrayList<ResponseItem>>, response: Response<ArrayList<ResponseItem>>) {
                //photos.clear()
                swipeRefreshLayout.isRefreshing = false
                photos.addAll(response.body()!!)
                homeTwoAdapter.notifyDataSetChanged()
                ProgressDialog.dismissProgress()
            }

            override fun onFailure(call: Call<ArrayList<ResponseItem>>, t: Throwable) {
                Log.d("@@@",t.message.toString())
                Toast.makeText(requireContext(), "No Internet Connection!", Toast.LENGTH_SHORT).show()
            }

        })
    }

    @JvmName("getPage1")
    fun getPage(): Int {
        return  ++page
    }

   fun refreshAdapter(photos: ArrayList<ResponseItem>){
     homeTwoAdapter = HomeTwoAdapter(photos)
     recyclerView?.adapter = homeTwoAdapter
   }

}