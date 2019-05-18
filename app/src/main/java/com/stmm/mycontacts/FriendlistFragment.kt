package com.stmm.mycontacts

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.stmm.mycontacts.data.AppDatabase
import com.stmm.mycontacts.data.MyFriendDao
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.friendlist_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FriendlistFragment : Fragment() {

    lateinit var listTeman: MutableList<MyFriend>

    private var db: AppDatabase? = null
    private var myFriendDao: MyFriendDao? = null
    lateinit var adapter: MyFriendAdapter


    companion object {
        fun newInstance(): FriendlistFragment {
            return FriendlistFragment()
        }
    }

    //private var listTeman : List<MyFriend>? = null


//lateinit artinya diinisialisasikan ketika program di jalankan.

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.friendlist_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabAdd.setOnClickListener {
            (activity as MainActivity).tampilAddFriendFragment()
        }
        listTeman = ArrayList()
        initLocalDB()
        initView()
        ambilDataTeman()
    }

    private fun initView() {
        initAdapter()
    }

    private fun initLocalDB() {
        db = AppDatabase.getAppDatabase(activity!!)
        myFriendDao = db?.myFriendDao()
    }

    private fun tampilToast(message: String) {
        Toast.makeText(activity!!, message, Toast.LENGTH_SHORT).show()
    }

    /*  private fun tampilTeman() {
          myfriend.layoutManager = LinearLayoutManager(activity)
          //pilih kayak gimana mau ditampilkan, bisa grid dll, disini pilihnya linear
          myfriend.adapter = MyFriendAdapter(
              activity!!,
              listTeman!!
          ) {
              val friend = it
          }
      } */
//ambil data teman dari DB

    //initrecycler view with adapter
    private fun initAdapter() {
        adapter = MyFriendAdapter(activity!!, listTeman)
        adapter.setMyFriendClickListener(object : MyFriendAdapter.MyFriendClickListener {
            override fun onLongClick(friend: MyFriend, position: Int) {
                confirmDialog(friend, position)
            }
        })

        myfriend.layoutManager = LinearLayoutManager(activity)
        myfriend.adapter = adapter
    }

    private fun ambilDataTeman() {
        listTeman = ArrayList()
        //ambil data teman dengan Livedata
        myFriendDao?.ambilSemuaDataTeman()?.observe(this, Observer { r ->
            listTeman = r.toMutableList()
            when {
                //check jika listnya kosong
                listTeman.size == 0 -> tampilToast("Belum ada data Teman")

                else -> {
                    initAdapter()
                }
            }
        })
    }


    private fun confirmDialog(friend: MyFriend, position: Int) {
        AlertDialog.Builder(activity!!)
            .setTitle("Delete ${friend.nama}")
            .setMessage("Do you want to delete ${friend.nama} ?")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(android.R.string.yes,
                DialogInterface.OnClickListener { dialog, whichButton ->
                    deleteFriend(friend)
                    adapter.notifyItemRemoved(position)
                }).show()
    }

    //delete friend dari database
    private fun deleteFriend(friend: MyFriend): Job {
        return GlobalScope.launch {
            myFriendDao?.deleteFriend(friend)
        }
    }
    /*private fun simulasiDataTeman() {
        listTeman = ArrayList()

        listTeman.add(MyFriend("Muhammad", "Laki-laki", "ada@gmail.com", "0812345678", "Bandung"))
        listTeman.add(MyFriend("Yanita", "Perempuan", "yanita@gmail.com", "085712434567", "Yogyakarta"))
    }*/

    //  fabAdd.setOnClickListener { }

}
