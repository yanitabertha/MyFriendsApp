package com.stmm.mycontacts

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MyFriend(

    val nama: String,
    val gender: String,
    val email: String,
    val telp: String,
    val alamat: String,

    @PrimaryKey(autoGenerate = true)
    val temanId: Int? = null

)