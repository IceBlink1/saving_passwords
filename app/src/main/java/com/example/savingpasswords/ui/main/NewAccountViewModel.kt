package com.example.savingpasswords.ui.main

import androidx.lifecycle.ViewModel
import com.example.savingpasswords.data.Account
import com.example.savingpasswords.domain.InsertAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

data class NewAccountState(
    val created: Boolean,
    val error: Throwable?
)

@HiltViewModel
class NewAccountViewModel @Inject constructor(
    private val createUseCase: InsertAccountUseCase
): ViewModel() {

    val stateFlow = MutableStateFlow(NewAccountState(false, null))

    fun create(domain: String, login: String, password: String) {
        createUseCase.execute(Account(0, domain, login, password)).doOnComplete {
            stateFlow.compareAndSet(stateFlow.value, NewAccountState(true, null))
        }.doOnError {
            stateFlow.compareAndSet(stateFlow.value, NewAccountState(false, it))
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()
    }

}