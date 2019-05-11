package com.stmm.mycontacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.friendlist_fragment.*

class FriendlistFragment: Fragment() {
    companion object {
        fun newInstance(): FriendlistFragment {
            return FriendlistFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.friendlist_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabAdd.setOnClickListener {
            (activity as MainActivity).tampilAddFriendFragment()
        }
    }

   // fabAdd.setOnClickListener { }

}