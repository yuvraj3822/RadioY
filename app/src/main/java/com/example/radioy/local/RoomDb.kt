package com.example.radioy.local

import com.tmsfalcon.device.tmsfalcon.database.dbKotlin.CurrentTable
import com.tmsfalcon.device.tmsfalcon.database.dbKotlin.RecentTable


import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.TypeConverters

/**
 * This is the database class where operation (dao) are defined
 *
 */

@Database(entities = arrayOf(CurrentTable::class,RecentTable::class),version = 1,exportSchema = false)
@TypeConverters(Converters::class)
public abstract  class RoomDb : RoomDatabase() {

    abstract fun currentDao(): CurrentDao
    abstract fun recentDao(): RecentDao

    /**
     * Singleton
     * To avoid multiple instances of database opening at the same time
     */
    companion object {

        @Volatile
        private var INSTANCE: RoomDb? = null

        fun getDatabase(context: Context): RoomDb {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDb::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}