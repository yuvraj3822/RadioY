package com.tmsfalcon.device.tmsfalcon.database.dbKotlin

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
/**
 * Its a kind of declaration of table where we can declare the
 * schema of table
 */
@Entity(tableName = "recent_table")
data class RecentTable(
    @PrimaryKey(autoGenerate = true) val id :Int? = 0,

    @ColumnInfo(name = "album") val album: String,
    @ColumnInfo(name = "artist") val artist: String,
    @ColumnInfo(name = "image_url") val image_url: String,
    @ColumnInfo(name = "link_url") val link_url: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "played_at") val played_at: String,
    @ColumnInfo(name = "preview_url") val preview_url: String,
    @ColumnInfo(name = "sid") val sid: String
)