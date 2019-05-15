package com.stmm.mycontacts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.my_friends_item.*

class MyFriendAdapter(
    private val context: Context,
    private val items: List<MyFriend>,
    private val listener: (MyFriend) -> Unit
) :
    RecyclerView.Adapter<MyFriendAdapter.ViewHolder>() {

    class ViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(item: MyFriend){

            txtFriendName.text = item.nama
            txtFriendEmail.text = item.email
            txtFriendTelp.text = item.telp

            /*  Glide.with(context).load(item.image).into((imgProfile)
              containerView.setOnClickListener ( listener(item) )  */
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.my_friends_item, parent, false)
        )
//untuk memilih mau ditampilkan seperti apa
    override fun getItemCount(): Int = items.size
//menyesuaikan ukuran data kita. kalo data 2, item countnya juga cuma 2. kalo kita isi 3 , berarti ga dinamis, hanya bs 3
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position])
    //untuk ambil data ketika data di attach
    }




}
