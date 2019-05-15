package com.stmm.mycontacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.stmm.mycontacts.data.AppDatabase
import com.stmm.mycontacts.data.MyFriendDao
import kotlinx.android.synthetic.main.friendlist_fragment.*

class FriendlistFragment : Fragment() {
    companion object {
        fun newInstance(): FriendlistFragment {
            return FriendlistFragment()
        }
    }

    //private var listTeman : List<MyFriend>? = null
    lateinit var listTeman: MutableList<MyFriend>

    private var db: AppDatabase? = null
    private var myFriendDao: MyFriendDao? = null

//lateinit artinya diinisialisasikan ketika program di jalankan.

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.friendlist_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabAdd.setOnClickListener {
            (activity as MainActivity).tampilAddFriendFragment()
        }
        // simulasiDataTeman()
        tampilTeman()
    }

    private fun initLocalDB() {
        db = AppDatabase.getAppDatabase(activity!!)
        myFriendDao = db?.myFriendDao()
    }

    private fun tampilToast(message: String) {
        Toast.makeText(activity!!, message, Toast.LENGTH_SHORT).show()
    }

    private fun tampilTeman() {
        myfriend.layoutManager = LinearLayoutManager(activity)
        //pilih kayak gimana mau ditampilkan, bisa grid dll, disini pilihnya linear
        myfriend.adapter = MyFriendAdapter(
            activity!!,
            listTeman!!
        ) {
            val friend = it
        }
    }

    /*private fun simulasiDataTeman() {
        listTeman = ArrayList()

        listTeman.add(MyFriend("Muhammad", "Laki-laki", "ada@gmail.com", "0812345678", "Bandung"))
        listTeman.add(MyFriend("Yanita", "Perempuan", "yanita@gmail.com", "085712434567", "Yogyakarta"))
    }*/

    // fabAdd.setOnClickListener { }

}
