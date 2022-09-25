package com.example.savingpasswords.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.savingpasswords.data.Account
import com.example.savingpasswords.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

data class MainViewModelState(
    val accounts: List<Account>,
    val isLoading: Boolean,
    val searchRequest: String,
    val error: Throwable?
) {
    companion object {
        fun empty() = MainViewModelState(
            accounts = emptyList(),
            isLoading = true,
            searchRequest = "",
            error = null
        )

        fun error(e: Throwable) = MainViewModelState(
            error = e,
            accounts = emptyList(),
            isLoading = false,
            searchRequest = ""
        )
    }
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val searchAccountUseCase: SearchAccountUseCase,
    private val updateAccountUseCase: UpdateAccountUseCase,
    private val insertAccountUseCase: InsertAccountUseCase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val stateFlow = MutableStateFlow(MainViewModelState.empty())

    fun init() {
        compositeDisposable.add(getAllAccountsUseCase.execute().doOnEach {
            stateFlow.compareAndSet(
                stateFlow.value,
                stateFlow.value.copy(accounts = it.value!!, isLoading = false, error = null)
            )
        }.doOnError {
            stateFlow.compareAndSet(stateFlow.value, MainViewModelState.error(it))
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe())
    }

    fun search(searchRequest: String) {
        compositeDisposable.add(searchAccountUseCase.execute(searchRequest).doOnEach {
            stateFlow.compareAndSet(
                stateFlow.value,
                stateFlow.value.copy(
                    accounts = it.value!!,
                    isLoading = false,
                    error = null,
                    searchRequest = searchRequest
                )
            )
        }.doOnError {
            stateFlow.compareAndSet(stateFlow.value, MainViewModelState.error(it))
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe())
    }

    fun insert(domain: String, accountName: String, password: String) {
        compositeDisposable.add(
            insertAccountUseCase.execute(
                Account(
                    0,
                    domain,
                    accountName,
                    password
                )
            ).doOnComplete {
                Log.d("d", "Inserted")
            }.doOnError {
                Log.e("d", "insert", it)
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()
        )
    }

    fun update(domain: String, accountName: String, password: String) {
        compositeDisposable.add(
            updateAccountUseCase.execute(
                Account(
                    0,
                    domain,
                    accountName,
                    password
                )
            ).doOnComplete {
                Log.d("d", "Updated")
            }.doOnError {
                Log.e("d", "update", it)
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()
        )
    }

    fun delete(account: Account) {
        compositeDisposable.add(
            deleteAccountUseCase.execute(
                account
            ).doOnComplete {
                Log.d("d", "Deleted")
            }.doOnError {
                Log.e("d", "delete", it)
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}