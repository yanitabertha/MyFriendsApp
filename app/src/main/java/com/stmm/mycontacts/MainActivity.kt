package com.stmm.mycontacts

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tampilFriendlistFragment()
   //     setSupportActionBar(toolbar)

    }

    private fun gantiFragment(fragmentManager: FragmentManager,
                              fragment: Fragment, frameId:Int) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(frameId, fragment)
        transaction.commit()
    }

        fun tampilFriendlistFragment(){
            gantiFragment(supportFragmentManager, FriendlistFragment.newInstance(),
                R.id.frameLayout)
        }

        fun tampilAddFriendFragment(){
            gantiFragment(supportFragmentManager, AddFriendFragment.newInstance(),
                R.id.frameLayout)
        }
    }

