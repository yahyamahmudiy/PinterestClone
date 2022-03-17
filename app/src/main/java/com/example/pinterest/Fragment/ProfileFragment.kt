package com.example.pinterest.Fragment

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pinterest.Adapter.PhotosAdapter
import com.example.pinterest.Database.AppDatabase
import com.example.pinterest.Helper.SpaceItemDecoration
import com.example.pinterest.Model.Photos
import com.example.pinterest.Networking.RetrofitHttp
import com.example.pinterest.R
import dev.matyaqubov.pinterest.service.model.SearchResultsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PhotosAdapter
    private lateinit var appDatabase: AppDatabase
    var word=""
    var sendData: SearchFragment.SendData? = null
    private lateinit var manager: StaggeredGridLayoutManager
    var list=ArrayList<Photos>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        initViews(view)

        return view
    }

    private fun initViews(view: View): View {
        appDatabase= AppDatabase.getInstance(requireContext())
        recyclerView=view.findViewById(R.id.recyclerView)

        manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        val decoration = SpaceItemDecoration(10)
        recyclerView.addItemDecoration(decoration)

        recyclerView.layoutManager = manager
        refreshAdapter(list)
        getAllPhotos()

        adapter.photoItemClick={
            word=it.word
            getThisPhoto(it.id)

        }

        return view
    }

    private fun getThisPhoto(id: String) {
        RetrofitHttp.posterService.getPhoto(id).enqueue(object : Callback<SearchResultsItem> {
            override fun onResponse(call: Call<SearchResultsItem>, response: Response<SearchResultsItem>) {
                if (response.body()!=null){
                    val photo=response.body()!!
                    sendData!!.sendPhoto(photo,word,1)
                }
            }

            override fun onFailure(call: Call<SearchResultsItem>, t: Throwable) {
                Toast.makeText(requireContext(), "Problem with internet", Toast.LENGTH_SHORT).show()
            }


        })
    }

    private fun getAllPhotos() {
        list.clear()
        list.addAll(appDatabase.photoDao().getAllPhotos())
        adapter.notifyDataSetChanged()
    }

    private fun refreshAdapter(list: ArrayList<Photos>) {
        adapter = PhotosAdapter(list)
        recyclerView.adapter = adapter
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