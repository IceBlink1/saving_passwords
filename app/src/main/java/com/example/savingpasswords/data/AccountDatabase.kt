package com.example.savingpasswords.data

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Room DB access class
 *
 */
@Database(entities = [Account::class], version = 1)
abstract class AccountDatabase : RoomDatabase() {
    abstract fun accountDatabase(): AccountDao
}