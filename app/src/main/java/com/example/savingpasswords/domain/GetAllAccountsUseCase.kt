package com.example.savingpasswords.domain

import com.example.savingpasswords.data.Account
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

/**
 * UseCase for getting all accounts
 *
 * @property accountRepository
 */
class GetAllAccountsUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {

    fun execute(): Flowable<List<Account>> {
        return accountRepository.getAll()
    }

}