package com.example.savingpasswords.data

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface AccountDao {

    @Query("SELECT * FROM account")
    fun getAll(): Flowable<List<Account>>

    @Query("SELECT * FROM account where domain like '%' || :domain || '%'")
    fun searchByName(domain: String): Flowable<List<Account>>

    @Delete
    fun delete(account: Account): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(account: Account): Completable

    @Insert
    fun insert(vararg accounts: Account): Completable
}