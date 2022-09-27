package com.example.savingpasswords.domain

import com.example.savingpasswords.data.Account
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

/**
 * Repository for accessing account-related stuff
 *
 */
interface AccountRepository {
    /**
     * Method for getting all accounts
     *
     * @return all known accounts
     */
    fun getAll(): Flowable<List<Account>>

    /**
     * Method for inserting new account into DB
     *
     * @param account account to be inserted
     * @return
     */
    fun insertNewAccount(account: Account): Completable

    /**
     * Method for updating an existing account
     *
     * @param account account to be updated
     * @return
     */
    fun updateAccount(account: Account): Completable

    /**
     * Method for deleting existing account
     *
     * @param account account to be deleted
     * @return
     */
    fun deleteAccount(account: Account): Completable

    /**
     * Method for searching on domain name as a substring
     *
     * @param searchRequest domain name to be searched
     * @return list of found accounts
     */
    fun searchByDomain(searchRequest: String): Flowable<List<Account>>
}