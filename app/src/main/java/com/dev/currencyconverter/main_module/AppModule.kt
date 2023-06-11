package com.dev.currencyconverter.main_module

import android.content.Context
import com.dev.currencyconverter.data_interfaqces.HistoryDao
import com.dev.currencyconverter.data_interfaqces.RatesDao
import com.dev.currencyconverter.database.MyRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun getDb(@ApplicationContext context: Context): MyRoomDatabase {
        return MyRoomDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun historyDao(db: MyRoomDatabase): HistoryDao {
        return db.historyDao()
    }

    @Singleton
    @Provides
    fun rateDao(db: MyRoomDatabase): RatesDao {
        return db.rateDao()
    }

}