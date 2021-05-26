package com.example.radioy.local

import com.tmsfalcon.device.tmsfalcon.database.dbKotlin.CurrentTable


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrentDao {

    /**
     * It is a kind of Querry class where
     * insertion deletion fetching and dynamic querry
     *  can be applied
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currentTable:CurrentTable):Long

    @Query("Select  * from current_table")
    fun getCurrentTable():MutableList<CurrentTable>


}