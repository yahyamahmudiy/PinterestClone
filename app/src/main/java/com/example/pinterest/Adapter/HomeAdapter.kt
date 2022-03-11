package com.example.pinterest.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pinterest.Fragment.Home.HomeFragmentAll
import com.example.pinterest.Fragment.Home.HomeFragmentAnimals
import com.example.pinterest.Fragment.Home.HomeFragmentForYou
import com.example.pinterest.Fragment.Home.HomeFragmentNature

class HomeAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle):FragmentStateAdapter(fragmentManager,lifecycle) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                return HomeFragmentAll()
            }
            1 -> {
                return HomeFragmentForYou()
            }
            2 -> {
                return HomeFragmentNature()
            }
            3 -> {
                return HomeFragmentAnimals()
            }
            else -> return Fragment()
        }
    }
}