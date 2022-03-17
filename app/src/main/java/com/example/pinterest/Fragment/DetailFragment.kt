package com.example.pinterest.Fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.pinterest.Adapter.SearchPhotosAdapter
import com.example.pinterest.Database.AppDatabase
import com.example.pinterest.Helper.ProgressDialog
import com.example.pinterest.Helper.SpaceItemDecoration
import com.example.pinterest.Model.Photos
import com.example.pinterest.Networking.RetrofitHttp
import com.example.pinterest.R
import dev.matyaqubov.pinterest.service.model.Search
import dev.matyaqubov.pinterest.service.model.SearchResultsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors


class DetailFragment : Fragment(){
    private lateinit var tv_username: TextView
    private lateinit var tv_username_comments:TextView
    private lateinit var tv_followers:TextView
    private lateinit var tv_description:TextView
    private lateinit var tv_alt_description:TextView
    private lateinit var tv_visit:TextView
    private lateinit var tv_save:TextView
    private lateinit var iv_main: ImageView
    private lateinit var iv_back:ImageView
    private lateinit var iv_profile:ImageView
    private lateinit var iv_profile_comments:ImageView
    private lateinit var iv_share:ImageView
    private var word :String=""
    lateinit var appDatabase: AppDatabase
    private var photo: SearchResultsItem?=null
    private lateinit var adapter: SearchPhotosAdapter
    private var sendData: SearchFragment.SendData? = null
    private var list = ArrayList<SearchResultsItem>()
    private var page=1
    lateinit var nestedScrollView: NestedScrollView
    lateinit var recyclerView: RecyclerView
    lateinit var manager: StaggeredGridLayoutManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        initViews(view)

        return view
    }

    fun initViews(view: View){
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = manager
        val decoration = SpaceItemDecoration(10)
        recyclerView.addItemDecoration(decoration)

        iv_profile=view.findViewById(R.id.iv_profile)
        tv_username=view.findViewById(R.id.tv_username)
        tv_username_comments=view.findViewById(R.id.tv_username_comments)
        tv_followers=view.findViewById(R.id.tv_followers)
        tv_description=view.findViewById(R.id.tv_description)
        tv_alt_description=view.findViewById(R.id.tv_alt_description)

        tv_save=view.findViewById(R.id.tv_save)
        iv_main=view.findViewById(R.id.iv_main)
        iv_back=view.findViewById(R.id.iv_back)
        iv_profile_comments=view.findViewById(R.id.iv_profile_comments)

        nestedScrollView = view.findViewById(R.id.nestedScrollView)


        setData()
        searchPhoto(word)

        refreshAdapter(list)


        nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v: NestedScrollView, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (v.getChildAt(v.childCount - 1) != null) {
                if (scrollY >= v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight && scrollY > oldScrollY) {
                    searchPhoto(word)
                }
            }
        } as NestedScrollView.OnScrollChangeListener)


        iv_back.setOnClickListener {
            requireActivity().onBackPressed()
        }

        adapter.photoItemClick = {
            sendData!!.sendPhoto(it,word,page)
        }

        savePhoto()
    }

    private fun setData() {
        Glide.with(iv_main.context)
            .load(photo!!.urls!!.smallS3)
            .placeholder(ColorDrawable(Color.parseColor(photo!!.color))).into(iv_main)

        Glide.with(iv_profile.context).load(photo!!.user!!.profileImage!!.small).into(iv_profile)

        Glide.with(iv_profile.context).load(photo!!.user!!.profileImage!!.small).into(iv_profile_comments)

        tv_username.text=photo!!.user!!.username
        tv_username_comments.text=photo!!.user!!.username
        tv_description.text=photo!!.description
        tv_alt_description.text=photo!!.altDescription

        manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = manager
        refreshAdapter(list)

    }

    fun savePhoto(){
        tv_save.setOnClickListener {

            tv_save.setBackgroundResource(R.drawable.round_shape_gray)
            tv_save.setTextColor(Color.BLACK)

            appDatabase= AppDatabase.getInstance(requireContext())

            var photos:Photos
            if (photo?.description == null) {
                 photos = Photos(
                    photo!!.id!!,
                    photo!!.color!!,
                    photo!!.description.let { " " },
                    photo!!.altDescription!!,
                    photo!!.urls!!.smallS3!!,
                    word
                )
            }else{
                 photos = Photos(
                    photo!!.id!!,
                    photo!!.color!!,
                    photo!!.description!!,
                    photo!!.altDescription!!,
                    photo!!.urls!!.smallS3!!,
                    word
                )
            }
            appDatabase.photoDao().savePhoto(photos)

        }

    }

    private fun refreshAdapter(list: ArrayList<SearchResultsItem>) {
        adapter = SearchPhotosAdapter(list)
        recyclerView.adapter = adapter
    }

    fun receivedData(photo:SearchResultsItem,word: String,page:Int){
        this.photo=photo
        this.word=word
        this.page=page
    }

    private fun searchPhoto(word: String) {
        ProgressDialog.showProgress(requireContext())
        RetrofitHttp.posterService.getSearchResult(word, getPage()).enqueue(object : Callback<Search> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(call: Call<Search>, response: Response<Search>) {

                    if (response.body()!!.results == null)
                        Toast.makeText(context, "LIMIT", Toast.LENGTH_SHORT).show()
                    else
                        list.addAll(response.body()!!.results!!)

                    ProgressDialog.dismissProgress()
                    Log.d("@@@", "onResponse: ${response.body()!!.results!!}")
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<Search>, t: Throwable) {
                    ProgressDialog.dismissProgress()
                    Toast.makeText(requireContext(), "No Internet Connection!", Toast.LENGTH_SHORT)
                        .show()
                }

            })

    }

    @JvmName("getPage1")
    private fun getPage(): Int {
        if (page < 250) {
            return page++
        } else {
            page = 1
            return page
        }
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