package com.example.radioy.local

import com.tmsfalcon.device.tmsfalcon.database.dbKotlin.RecentTable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecentDao {
    /**
     * It is a kind of Querry class where
     * insertion deletion fetching and dynamic querry
     *  can be applied
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recentTable:RecentTable):Long

    @Query("Select  * from recent_table ")
    fun getRecent():MutableList<RecentTable>


}