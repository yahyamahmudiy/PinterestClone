package com.example.pinterest.Activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.pinterest.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.pinterest.Fragment.Chats.ChatsFragment
import com.example.pinterest.Fragment.DetailFragment
import com.example.pinterest.Fragment.Home.HomeFragment
import com.example.pinterest.Fragment.ProfileFragment
import com.example.pinterest.Fragment.SearchFragment
import dev.matyaqubov.pinterest.service.model.SearchResultsItem

class MainActivity : AppCompatActivity(), SearchFragment.SendData{
    lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    fun initViews() {
        bottomNavigationView = findViewById(R.id.bottom_navigation)

        /*val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_main) as NavHostFragment
        bottomNavigationView.setupWithNavController(navHostFragment.navController)*/


        val firstFragment= HomeFragment()
        val secondFragment=SearchFragment()
        val thirdFragment= ChatsFragment()
        val fourthFragment= ProfileFragment()

        setCurrentFragment(firstFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            var fragment: Fragment
            when(it.itemId){
                R.id.homeFragment->setCurrentFragment(firstFragment)
                R.id.searchFragment->setCurrentFragment(secondFragment)
                R.id.chatsFragment->setCurrentFragment(thirdFragment)
                R.id.profileFragment->setCurrentFragment(fourthFragment)
            }
            true
        }
    }
    private fun setCurrentFragment(fragment:Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout,fragment)
            addToBackStack("back")
            commit()
        }


    override fun sendPhoto(photo: SearchResultsItem, word: String, page: Int) {
        val detailsFragment = DetailFragment()
        detailsFragment.receivedData(photo, word,page)
        setCurrentFragment(detailsFragment)
    }

}