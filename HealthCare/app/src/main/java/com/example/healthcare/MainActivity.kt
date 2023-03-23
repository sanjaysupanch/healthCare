package com.example.healthcare

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.healthcare.features.blog.ui.BlogsFragment
import com.example.healthcare.features.dash.ui.DashboardFragment
import com.example.healthcare.features.states.ui.StatesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(DashboardFragment())

        bottomNav = findViewById(R.id.bottomNav)

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_menu -> {
                    loadFragment(DashboardFragment())
                }
                R.id.navigation_blog -> {
                    loadFragment(BlogsFragment())
                }
                R.id.navigation_states -> {
                    loadFragment(StatesFragment())
                }
            }
            true
        }
    }


    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}