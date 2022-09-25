package com.example.savingpasswords.domain

import com.example.savingpasswords.data.Account
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

interface AccountRepository {
    fun getAll(): Flowable<List<Account>>

    fun insertNewAccount(account: Account): Completable

    fun updateAccount(account: Account): Completable

    fun deleteAccount(account: Account): Completable

    fun searchByDomain(searchRequest: String): Flowable<List<Account>>
}