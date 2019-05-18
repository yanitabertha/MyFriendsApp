package com.stmm.mycontacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.stmm.mycontacts.data.AppDatabase
import com.stmm.mycontacts.data.MyFriendDao
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.add_friend_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AddFriendFragment : Fragment() {
    companion object {
        fun newInstance(): AddFriendFragment {
            return AddFriendFragment()
        }
    }

    private var namaInput: String = ""
    private var emailInput: String = ""
    private var telpInput: String = ""
    private var alamatInput: String = ""
    private var genderInput: String = ""
    // private var imageBytes: ByteArray? = null


    private var db: AppDatabase? = null
    private var myFriendDao: MyFriendDao? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_friend_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAdd.setOnClickListener {
            validasiInput()
        }
        initLocalDB()
    }

    private fun initLocalDB() {
        db = AppDatabase.getAppDatabase(activity!!)
        myFriendDao = db?.myFriendDao()
    }

    /*   private fun setDataSpinnerGender() {
           val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
               this,
               R.array.gender_list, android.R.layout.simple_spinner_dropdown_item
           )

           spinnerGender.adapter = adapter
       } */
    private fun tampilToast(message: String) {
        Toast.makeText(activity!!, message, Toast.LENGTH_SHORT).show()
    }

    private fun validasiInput() {
        namaInput = edtName.text.toString()
        emailInput = edtEmail.text.toString()
        telpInput = edtTelp.text.toString()
        alamatInput = edtAddress.text.toString()
        genderInput = spinnerGender.selectedItem.toString()

        when {

            namaInput.isEmpty() -> edtName.error = "Nama tidak boleh kosong"
            genderInput.equals("Pilih Kelamin") -> tampilToast("Kelamin harus dipilih")
            emailInput.isEmpty() -> edtEmail.error = "Email harus diisi"
            telpInput.isEmpty() -> edtTelp.error = "No Telepon tidak boleh kosong"
            alamatInput.isEmpty() -> edtAddress.error = "Alamat tidak boleh kosong"


            else -> {
                //insert ke db
                val friend = MyFriend(namaInput, genderInput, emailInput, telpInput, alamatInput)
                tambahDataTeman(friend)
            }

        }
    }
//fungsi untuk menambahkan data teman
    private fun tambahDataTeman(friend: MyFriend) : Job {
        return  GlobalScope.launch {
            myFriendDao?.tambahTeman(friend)
            (activity as MainActivity). tampilFriendlistFragment()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }
}
