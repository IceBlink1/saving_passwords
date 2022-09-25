package com.example.savingpasswords.domain

import com.example.savingpasswords.data.Account
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(private val accountRepository: AccountRepository) {

    fun execute(account: Account): Completable {
        return accountRepository.deleteAccount(account)
    }
}