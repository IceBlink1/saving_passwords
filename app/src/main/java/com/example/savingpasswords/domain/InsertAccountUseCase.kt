package com.example.savingpasswords.domain

import com.example.savingpasswords.data.Account
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

/**
 * UseCase for inserting accounts
 *
 * @property repository
 */
class InsertAccountUseCase @Inject constructor(private val repository: AccountRepository) {

    fun execute(account: Account): Completable {
        return repository.insertNewAccount(account)
    }

}