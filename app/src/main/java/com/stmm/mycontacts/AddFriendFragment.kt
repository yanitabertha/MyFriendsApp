package com.stmm.mycontacts

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.fondesa.kpermissions.builder.PermissionRequestBuilder
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.request.PermissionRequest
import com.stmm.mycontacts.data.AppDatabase
import com.stmm.mycontacts.data.MyFriendDao
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.add_friend_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.IOException

class AddFriendFragment : Fragment(), PermissionRequest.AcceptedListener, PermissionRequest.DeniedListener {

    override fun onPermissionsAccepted(permissions: Array<out String>) {
        showPictureDialog()
    }

    override fun onPermissionsDenied(permissions: Array<out String>) {
        requestPermission()
    }

    private val GALLERY = 1
    private val CAMERA = 2

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
    private var imageByte: ByteArray? = null
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
        initView()
        // imgProfile.setOnClickListener{checkVersiAndroid()}
    }

    private fun initLocalDB() {
        db = AppDatabase.getAppDatabase(activity!!)
        myFriendDao = db?.myFriendDao()
    }

    private fun initView() {
        btnAdd.setOnClickListener { validasiInput() }

        imgProfile.setOnClickListener { checkVersiAndroid() }
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

            imageByte == null -> tampilToast("Gambar tidak boleh kosong")
            namaInput.isEmpty() -> edtName.error = "Nama tidak boleh kosong"
            genderInput.equals("Pilih Kelamin") -> tampilToast("Kelamin harus dipilih")
            emailInput.isEmpty() -> edtEmail.error = "Email harus diisi"
            telpInput.isEmpty() -> edtTelp.error = "No Telepon tidak boleh kosong"
            alamatInput.isEmpty() -> edtAddress.error = "Alamat tidak boleh kosong"


            else -> {
                //insert ke db
                val friend = MyFriend(
                    nama = namaInput,
                    gender = genderInput,
                    email = emailInput,
                    telp = telpInput,
                    alamat = alamatInput,
                    image = imageByte
                )
                tambahDataTeman(friend)
            }

        }
    }

    //fungsi untuk menambahkan data teman
    private fun tambahDataTeman(friend: MyFriend): Job {
        return GlobalScope.launch {
            myFriendDao?.tambahTeman(friend)
            (activity as MainActivity).tampilFriendlistFragment()
        }
    }


    fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(activity!!)
        pictureDialog.setTitle("Silahkan Pilih")
        val pictureDialogItems = arrayOf("Ambil foto dari galeri", "ambil foto dengan kamera")
        pictureDialog.setItems(pictureDialogItems) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallery()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }

    private fun requestPermission() {
        val request = permissionsBuilder(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE

        ).build()

        request.acceptedListener(this)
        request.deniedListener(this)
        request.send()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY) {
            if (data != null) {
                val contentURI = data.data
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        activity!!.contentResolver, contentURI
                    )
                    setImageProfile(bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(
                        activity, "Failed!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else if (requestCode == CAMERA) {
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            setImageProfile(thumbnail)
        }
    }

    private fun checkVersiAndroid() {
        if (android.os.Build.VERSION.SDK_INT >=
            android.os.Build.VERSION_CODES.M
        ) {
            requestPermission()
        } else {
            showPictureDialog()
        }
    }

    //nah ini untuk kalo misalnya versi di atas M (MARSMELLOW) maka akan minta izin, bgitu pun sebaliknya
    //karena di bawah marshmellow , izinnya diminta saat aplikasi dijalankan

    fun choosePhotoFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY)
    }

    fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)

    }

    fun setImageProfile(bitmap: Bitmap) {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)

        imgProfile.setImageBitmap(
            BitmapFactory.decodeByteArray(
                stream.toByteArray(), 0, stream.toByteArray().size
            )
        )

        imageByte = stream.toByteArray()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }
}
