package com.stmm.mycontacts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.my_friends_item.*
import kotlinx.android.synthetic.main.my_friends_item.view.*

class MyFriendAdapter(
    private val context: Context,
    private val items: List<MyFriend>
) :
    RecyclerView.Adapter<MyFriendAdapter.ViewHolder>() {

    class ViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(item: MyFriend, listener: MyFriendClickListener, position: Int) {

            containerView.txtFriendName.text = item.nama
            containerView.txtFriendEmail.text = item.email
            containerView.txtFriendTelp.text = item.telp
            containerView.container.setOnLongClickListener(object : View.OnLongClickListener {
                //artinya jika pada id container (dalam hal ini adalah parentnya) di long click, maka
                //akan terjadi sebuah activity (dalam hal ini, dialog box)
                override fun onLongClick(v: View?): Boolean {
                    listener.onLongClick(item, position)
                    return true
                }
            })

            /*  Glide.with(context).load(item.image).into((imgProfile)
              containerView.setOnClickListener ( listener(item) )  */
        }
    }

    private lateinit var listener: MyFriendClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.my_friends_item, parent, false)
        )

    //untuk memilih mau ditampilkan seperti apa
    override fun getItemCount(): Int = items.size

    fun setMyFriendClickListener(listener: MyFriendClickListener) {
        this.listener = listener
    }

    //menyesuaikan ukuran data kita. kalo data 2, item countnya juga cuma 2. kalo kita isi 3 , berarti ga dinamis, hanya bs 3
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items.get(position), listener, holder.adapterPosition)
        //untuk ambil data ketika data di attach
    }

    interface MyFriendClickListener {
        fun onLongClick(friend: MyFriend, position: Int)
    }

}
