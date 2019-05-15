package com.stmm.mycontacts.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stmm.mycontacts.MyFriend

@Dao
interface MyFriendDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun tambahTeman(friend: MyFriend)

    @Query("SELECT * FROM MyFriend")
    fun ambilSemuaTeman(): LiveData<List<MyFriend>>
}