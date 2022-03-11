package com.example.pinterest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pinterest.Adapter.HomeTwoAdapter
import com.example.pinterest.Helper.EndlessRecyclerViewScrollListener
import com.example.pinterest.Helper.ProgressDialog
import com.example.pinterest.Networking.ResponseItem
import com.example.pinterest.Networking.RetrofitHttp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.logging.Filter

class HomeFragment : Fragment() {
    private lateinit var rv_filter: RecyclerView
    private lateinit var rv_home_main: RecyclerView
    private lateinit var filters: ArrayList<Filter>
    var list = ArrayList<ResponseItem>()
    var page = 0
    private lateinit var adapter: HomeTwoAdapter
    private lateinit var manager: StaggeredGridLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
  /*  private fun initViews(view: View): View {
        prepareFilters()
        rv_filter = view.findViewById(R.id.rv_filter)
        rv_filter.adapter = FilterAdapter(filters)
        rv_home_main = view.findViewById(R.id.rv_home_main)
        manager=StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rv_home_main.layoutManager = manager
        refreshAdapter(list)
        getPhotoFromServer()

        val scrollListener=object : EndlessRecyclerViewScrollListener(manager){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                getPhotoFromServer()
            }

        }
        rv_home_main.addOnScrollListener(scrollListener)
        return view
    }

    fun getPhotoFromServer() {
        ProgressDialog.showProgress(requireContext())
        RetrofitHttp.apiService.getPhotos(getPage())
            .enqueue(object : Callback<ArrayList<ResponseItem>> {
                override fun onResponse(call: Call<ArrayList<ResponseItem>>, response: Response<ArrayList<ResponseItem>>
                ) {
                    list.addAll(response.body()!!)
                    adapter.notifyDataSetChanged()
                    ProgressDialog.dismissProgress()
                }

                override fun onFailure(call: Call<ArrayList<ResponseItem>>, t: Throwable) {
                    ProgressDialog.dismissProgress()
                    Toast.makeText(requireContext(), "Intenet bilan muammo", Toast.LENGTH_SHORT)
                        .show()
                }

            })
    }

    @JvmName("getPage1")
    fun getPage(): Int {
        return if (page < 100) page + 1 else page
    }


    private fun refreshAdapter(list: ArrayList<ResponseItem>) {
        adapter = HomeTwoAdapter(list)
        rv_home_main.adapter = adapter
    }

    private fun prepareFilters() {
        filters = ArrayList<Filter>()
        filters.add(Filter("All", true))
        filters.add(Filter("Motivation"))
    }*/


}