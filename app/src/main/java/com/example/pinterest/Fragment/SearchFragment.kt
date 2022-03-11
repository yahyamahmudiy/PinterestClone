package com.example.pinterest.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pinterest.Adapter.SearchAdapter
import com.example.pinterest.Model.Home
import com.example.pinterest.R
import java.util.ArrayList
import android.view.inputmethod.EditorInfo

import android.view.KeyEvent
import android.widget.TextView.OnEditorActionListener
import androidx.core.content.ContextCompat.getSystemService

import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pinterest.Adapter.SearchPhotosAdapter
import com.example.pinterest.Helper.EndlessRecyclerViewScrollListener
import com.example.pinterest.Helper.ProgressDialog
import com.example.pinterest.Helper.SpaceItemDecoration
import com.example.pinterest.Networking.RetrofitHttp
import dev.matyaqubov.pinterest.service.model.Search
import dev.matyaqubov.pinterest.service.model.SearchResultsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchFragment : Fragment() {
    private var photosOne = ArrayList<Home>()
    private var photosTwo = ArrayList<Home>()
    var list = ArrayList<SearchResultsItem>()
    private lateinit var searchAdapterOne:SearchAdapter
    private lateinit var searchAdapterTwo:SearchAdapter
    lateinit var adapter: SearchPhotosAdapter
    private lateinit var manager: StaggeredGridLayoutManager
    var page = 1
    private var word: String = ""
    private var recyclerViewOne: RecyclerView? = null
    private var recyclerViewTwo: RecyclerView? = null
    private var recyclerView:RecyclerView?=null
    lateinit var editText:EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        photosOne.add(Home("https://i.pinimg.com/236x/2a/77/70/2a7770e880c000ab530b7c889556ef3a.jpg", "Import Cars"))
        photosOne.add(Home("https://i.pinimg.com/564x/42/46/95/4246952430136a8a88551008c93b4224.jpg", "Studio setup"))
        photosOne.add(Home("https://i.pinimg.com/236x/b0/6f/09/b06f09d953a2a9b65c8c517f7ef8d1ef.jpg", "Camera Gear"))
        photosOne.add(Home("https://i.pinimg.com/236x/58/2e/94/582e94408f3d0ff2b13a94de48e2cfb7.jpg", "Truck bed camping"))
        photosOne.add(Home("https://i.pinimg.com/236x/09/f2/a8/09f2a8a98430c8d410bfd305b4d1973d.jpg", "Elliot erwitt"))
        photosOne.add(Home("https://i.pinimg.com/236x/46/44/6f/46446fdde15fe8ed6ad5aeef1b028d3c.jpg", "Afghan patterns"))
        photosOne.add(Home("https://i.pinimg.com/236x/c9/e4/5f/c9e45f8333ad25883019b2f13f718922.jpg", "Tennis shoes"))
        photosOne.add(Home("https://i.pinimg.com/236x/7c/61/14/7c611403c6eca5d00809b82e85cb0bb9.jpg", "Girls sneakers"))

        photosTwo.add(Home("https://i.pinimg.com/236x/06/cd/dd/06cddd0c5fe356b96f810d3fae7e10de.jpg", "Iphone Wallpaper"))
        photosTwo.add(Home("https://i.pinimg.com/236x/1e/ad/95/1ead95803a35f3ee4888e196a5618e1a.jpg", "Short hairstyle women"))
        photosTwo.add(Home("https://i.pinimg.com/236x/f6/cb/c4/f6cbc44893e481c0e9570980a7dc62c8.jpg", "Blue aesthetic"))
        photosTwo.add(Home("https://i.pinimg.com/236x/36/b4/6a/36b46a20e3a898c210c823c18d443d9a.jpg", "Digital art girl"))
        photosTwo.add(Home("https://i.pinimg.com/236x/48/19/8f/48198f333a0778c4bc1b5987408c2eae.jpg", "Elf on the shelf ideas"))
        photosTwo.add(Home("https://i.pinimg.com/236x/d2/9a/0e/d29a0eb76e27bb4d218cc2d8e382cc49.jpg", "Easy drawings"))

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view:View = inflater.inflate(R.layout.fragment_search, container, false)

        initViews(view)

        return view
    }

    @SuppressLint("SetTextI18n")
    fun initViews(view: View){
        recyclerViewOne = view.findViewById(R.id.recyclerViewOne)
        recyclerViewOne?.setLayoutManager(GridLayoutManager(context,2))

        recyclerViewTwo = view.findViewById(R.id.recyclerViewTwo)
        recyclerViewTwo?.setLayoutManager(GridLayoutManager(context,2))

        refreshAdapterOne()
        refreshAdapterTwo()

        recyclerView = view.findViewById(R.id.recyclerView)
        manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView!!.layoutManager = manager

        val decoration = SpaceItemDecoration(10)
        recyclerView?.addItemDecoration(decoration)

        refreshAdapter(list)

        val scrollListener = object : EndlessRecyclerViewScrollListener(manager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                searchPhoto(word)
            }
        }
        recyclerView!!.addOnScrollListener(scrollListener)

        val textViewCancel:TextView = view.findViewById(R.id.textViewCancel)
        val nestedScrollView:NestedScrollView = view.findViewById(R.id.nestedScrollView)
        editText = view.findViewById(R.id.editText)

        searchAdapterOne.clickitem={
            nestedScrollView.visibility=View.GONE
            list.clear()
            word = it
            editText.setText(word)
            searchPhoto(it)
        }

        searchAdapterTwo.clickitem={
            nestedScrollView.visibility=View.GONE
            word = it
            list.clear()
            editText.setText(word)
            searchPhoto(it)
        }

        editText.addTextChangedListener {
            textViewCancel.visibility = View.VISIBLE
            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_camera, 0)
            word = it.toString()

            nestedScrollView.visibility = View.GONE
        }

        editText.setOnEditorActionListener { _, actionId, keyEvent ->
            if ((keyEvent != null && (keyEvent.keyCode == KeyEvent.KEYCODE_ENTER))|| (actionId == EditorInfo.IME_ACTION_SEARCH) ) {
                list.clear()
                searchPhoto(word)
            }
            false
        }

        textViewCancel.setOnClickListener {
            editText.setText("")
            textViewCancel.visibility = View.GONE
            nestedScrollView.visibility = View.VISIBLE
        }

    }

    private fun searchPhoto(word: String) {
        ProgressDialog.showProgress(requireContext())
        RetrofitHttp.posterService.getSearchResult(word,getPage()).enqueue(object : Callback<Search> {
                override fun onResponse(call: Call<Search>, response: Response<Search>) {
                    list.addAll(response.body()!!.results!!)
                    Log.d("requessssst", "onResponse: ${response.body()!!.results!!}")
                    adapter.notifyDataSetChanged()
                    ProgressDialog.dismissProgress()
                }

                override fun onFailure(call: Call<Search>, t: Throwable) {
                    ProgressDialog.dismissProgress()
                    Toast.makeText(requireContext(), "Check internet please", Toast.LENGTH_SHORT).show()
                }

            })

    }

    private fun refreshAdapter(list: ArrayList<SearchResultsItem>) {
        adapter = SearchPhotosAdapter(list)
        recyclerView!!.adapter = adapter
    }
    fun refreshAdapterOne(){
        searchAdapterOne = SearchAdapter(photosOne)
        recyclerViewOne?.setAdapter(searchAdapterOne)
    }

    fun refreshAdapterTwo(){
         searchAdapterTwo = SearchAdapter(photosTwo)
        recyclerViewTwo?.setAdapter(searchAdapterTwo)
    }

    @JvmName("getPage1")
    private fun getPage(): Int {
        return ++page
    }

}