package com.example.namhuan.muzicplayer

import android.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.R.attr.fragment


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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.getItemId()
       if( id == R.id.addMusic){

           replaceFragment(AddMusicFragment())
       }
        return  super.onOptionsItemSelected(item)
    }
}

