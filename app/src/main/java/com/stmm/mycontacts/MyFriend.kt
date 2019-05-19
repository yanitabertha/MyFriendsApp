package com.stmm.mycontacts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MyFriend(

    @PrimaryKey(autoGenerate = true)
    val temanId: Int? = null,

    val nama: String,
    val gender: String,
    val email: String,
    val telp: String,
    val alamat: String,

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var image: ByteArray? = null



)