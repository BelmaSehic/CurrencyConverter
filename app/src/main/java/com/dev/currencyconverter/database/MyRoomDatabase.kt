package com.dev.currencyconverter.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dev.currencyconverter.data_interfaqces.HistoryDao
import com.dev.currencyconverter.data_interfaqces.RatesDao
import com.dev.currencyconverter.tables.History
import com.dev.currencyconverter.tables.Rates

@Database(entities = [History::class, Rates::class], version = 1, exportSchema = false)
abstract class MyRoomDatabase : RoomDatabase() {

    abstract fun rateDao(): RatesDao
    abstract fun historyDao(): HistoryDao

    companion object {

        @Volatile
        var INSTANCE: MyRoomDatabase? = null

        fun getInstance(context: Context): MyRoomDatabase {
            var instance = INSTANCE
            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyRoomDatabase::class.java,
                        "details_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return INSTANCE!!
            }
        }

    }
}