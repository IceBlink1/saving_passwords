package com.example.savingpasswords.domain

import com.example.savingpasswords.data.Account
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class SearchAccountUseCase @Inject constructor(private val repository: AccountRepository) {

    fun execute(searchRequest: String): Flowable<List<Account>> {
        return repository.searchByDomain(searchRequest)
    }

}