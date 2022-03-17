package com.example.pinterest.Fragment.Home

import android.annotation.SuppressLint
import android.app.Activity
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
import com.example.pinterest.Adapter.SearchPhotosAdapter
import com.example.pinterest.Fragment.DetailFragment
import com.example.pinterest.Fragment.SearchFragment
import com.example.pinterest.Helper.EndlessRecyclerViewScrollListener
import com.example.pinterest.Helper.ProgressDialog
import com.example.pinterest.Helper.SpaceItemDecoration
import com.example.pinterest.Networking.ResponseItem
import com.example.pinterest.Networking.RetrofitHttp
import com.example.pinterest.R
import dev.matyaqubov.pinterest.service.model.Search
import dev.matyaqubov.pinterest.service.model.SearchResultsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragmentAll : Fragment() {
    private var photos = ArrayList<SearchResultsItem>()
    private var recyclerView: RecyclerView? = null
    var page = 1
    private var word: String = ""
    private lateinit var manager: StaggeredGridLayoutManager
    lateinit var swipeRefreshLayout:SwipeRefreshLayout
    lateinit var adapter: SearchPhotosAdapter
    var sendData: SearchFragment.SendData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view:View = inflater.inflate(R.layout.fragment_home_all, container, false)

        recyclerView = view.findViewById<View>(R.id.recyclerView) as RecyclerView
        manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        val decoration = SpaceItemDecoration(10)
        recyclerView?.addItemDecoration(decoration)


        recyclerView!!.layoutManager = manager

        word = "random"

        refreshAdapter(photos)
        apiPosterListRetrofit(word)

        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh2)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            photos.clear()
            apiPosterListRetrofit(word)

        }



        val scrollListener=object : EndlessRecyclerViewScrollListener(manager){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                apiPosterListRetrofit(word)
            }

        }
        recyclerView!!.addOnScrollListener(scrollListener)

        adapter.photoItemClick={
            sendData?.sendPhoto(it,word,page)
        }

        return view
    }

    fun apiPosterListRetrofit(word:String){
        ProgressDialog.showProgress(requireContext())
        RetrofitHttp.posterService.getSearchResult(word,getPage()).enqueue(object : Callback<Search> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<Search>, response: Response<Search>) {
                swipeRefreshLayout.isRefreshing = false
                if (!response.body()!!.results.isNullOrEmpty()){
                    photos.addAll(response.body()!!.results!!)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(requireContext(), "The End", Toast.LENGTH_SHORT).show()
                }
                ProgressDialog.dismissProgress()
            }

            override fun onFailure(call: Call<Search>, t: Throwable) {
                Log.d("@@@",t.message.toString())
                Toast.makeText(requireContext(), "No Internet Connection!", Toast.LENGTH_SHORT).show()
            }

        })
    }

    @JvmName("getPage1")
    fun getPage(): Int {
        return  ++page
    }

    fun refreshAdapter(photos: ArrayList<SearchResultsItem>){
       adapter = SearchPhotosAdapter(photos)
       recyclerView?.adapter = adapter
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        try {
            sendData = activity as SearchFragment.SendData
        } catch (e: Exception) {
            Toast.makeText(requireContext(), " must implement", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDetach() {
        sendData = null
        super.onDetach()
    }

}