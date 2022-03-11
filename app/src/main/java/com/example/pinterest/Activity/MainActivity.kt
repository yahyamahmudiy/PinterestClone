package com.example.pinterest.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pinterest.R
import com.google.android.material.bottomnavigation.BottomNavigationView

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController


class MainActivity : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    fun initViews() {
        bottomNavigationView = findViewById(R.id.bottom_navigation)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_main) as NavHostFragment
        bottomNavigationView.setupWithNavController(navHostFragment.navController)

        /*val firstFragment= HomeFragment()
        val secondFragment=SearchFragment()
        val thirdFragment=ChatsFragment()
        val fourthFragment=ProfileFragment()

        setCurrentFragment(firstFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            var fragment:Fragment
            when(it.itemId){
                R.id.nav_home->setCurrentFragment(firstFragment)
                R.id.nav_search->setCurrentFragment(secondFragment)
                R.id.nav_chat->setCurrentFragment(thirdFragment)
                R.id.nav_person->setCurrentFragment(fourthFragment)
            }
            true
        }
    }
    private fun setCurrentFragment(fragment:Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout,fragment)
            commit()
        }*/
    }
}