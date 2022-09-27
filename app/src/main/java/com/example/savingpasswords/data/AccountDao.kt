package com.example.savingpasswords.data

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

/**
 * Interface for generating an object for DB access
 *
 */
@Dao
interface AccountDao {

    /**
     * Method for requesting all accounts from DB
     *
     * @return all accounts from DB
     */
    @Query("SELECT * FROM account")
    fun getAll(): Flowable<List<Account>>

    /**
     * Method for searching accounts by domain name
     *
     * @param domain request, substring search
     * @return all accountable accounts
     */
    @Query("SELECT * FROM account where domain like '%' || :domain || '%'")
    fun searchByName(domain: String): Flowable<List<Account>>

    /**
     * Method for deleting an account
     *
     * @param account account to delete
     * @return
     */
    @Delete
    fun delete(account: Account): Completable

    /**
     * Method for updating account
     *
     * @param account account to be updated
     * @return
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(account: Account): Completable

    /**
     * Method for inserting accounts into DB
     *
     * @param accounts accounts to be inserted
     * @return
     */
    @Insert
    fun insert(vararg accounts: Account): Completable
}