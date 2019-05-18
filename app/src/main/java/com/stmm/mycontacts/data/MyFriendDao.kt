package com.stmm.mycontacts.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.stmm.mycontacts.MyFriend

@Dao
interface MyFriendDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun tambahTeman(friend: MyFriend)

    @Query("SELECT * FROM MyFriend")
    fun ambilSemuaDataTeman(): LiveData<List<MyFriend>>

    @Delete
    fun deleteFriend (friend: MyFriend)
}