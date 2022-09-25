package com.example.savingpasswords.data

import com.example.savingpasswords.domain.AccountRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountDao: AccountDao
) : AccountRepository {
    override fun getAll(): Flowable<List<Account>> {
        return accountDao.getAll()
    }

    override fun searchByDomain(searchRequest: String): Flowable<List<Account>> {
        return accountDao.searchByName(searchRequest)
    }

    override fun insertNewAccount(account: Account): Completable {
        return accountDao.insert(account)
    }

    override fun updateAccount(account: Account): Completable {
        return accountDao.update(account)
    }

    override fun deleteAccount(account: Account): Completable {
        return accountDao.delete(
            account
        )
    }
}