package com.example.namhuan.muzicplayer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        if (savedInstanceState == null) {

            supportFragmentManager

                .beginTransaction()

                .add(R.id.frame_container, PlayListFragment(), "PlayList")

                .commit()
        }

    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_menu -> {
                replaceFragment(PlayListFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_play -> {
                replaceFragment(PlayingFragment())
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }

    private fun replaceFragment(fragment: android.support.v4.app.Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_container, fragment)
        fragmentTransaction.commit()

    }
}
