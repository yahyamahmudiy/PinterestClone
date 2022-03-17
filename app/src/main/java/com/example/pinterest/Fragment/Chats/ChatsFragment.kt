package com.example.pinterest.Fragment.Chats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import com.example.pinterest.Adapter.ChatAdapter
import com.example.pinterest.R
import com.google.android.material.tabs.TabLayout

class ChatsFragment : Fragment() {
    private lateinit var viewPager2: ViewPager2
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_chats, container, false)

        initViews(view)

        return view
    }

    fun initViews(view: View){
        val icon = view.findViewById<ImageView>(R.id.icon)

        viewPager2 = view.findViewById(R.id.viewPager)
        tabLayout = view.findViewById(R.id.tab_layout)
        chatAdapter = ChatAdapter(childFragmentManager, lifecycle)
        viewPager2.adapter = chatAdapter

        tabLayout.addTab(tabLayout.newTab().setText("Updates"))
        tabLayout.addTab(tabLayout.newTab().setText("Messages"))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager2.currentItem = tab!!.position
                if (tab.position == 1){
                    icon.visibility = View.GONE
                }else{
                    icon.visibility = View.VISIBLE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
    }
}