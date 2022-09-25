package com.example.savingpasswords.domain

import com.example.savingpasswords.data.Account
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class GetAllAccountsUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {

    fun execute(): Flowable<List<Account>> {
        return accountRepository.getAll()
    }

}