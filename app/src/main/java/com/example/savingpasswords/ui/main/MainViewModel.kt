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

/**
 * Data class which represents the state of MainFragment
 *
 * @property accounts currently shown accounts
 * @property isLoading are accounts loading
 * @property error if an error occured it should be shown
 */
data class MainViewModelState(
    val accounts: List<Account>,
    val isLoading: Boolean,
    val error: Throwable?
) {
    companion object {
        fun empty() = MainViewModelState(
            accounts = emptyList(),
            isLoading = true,
            error = null
        )

        fun error(e: Throwable) = MainViewModelState(
            error = e,
            accounts = emptyList(),
            isLoading = false,
        )
    }
}

/**
 * ViewModel paired with MainFragment, exposes state for MainFragment to listen to
 *
 * @property deleteAccountUseCase
 * @property getAllAccountsUseCase
 * @property searchAccountUseCase
 * @property updateAccountUseCase
 * @property insertAccountUseCase
 */
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

    /**
     * Subscription to getAll usecase
     *
     */
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

    /**
     * search button callback
     *
     * @param searchRequest
     */
    fun search(searchRequest: String) {
        compositeDisposable.add(searchAccountUseCase.execute(searchRequest).doOnEach {
            stateFlow.compareAndSet(
                stateFlow.value,
                stateFlow.value.copy(
                    accounts = it.value!!,
                    isLoading = false,
                    error = null,
                )
            )
        }.doOnError {
            stateFlow.compareAndSet(stateFlow.value, MainViewModelState.error(it))
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe())
    }

    /**
     * Callback for updating certain accounts
     *
     * @param domain domain of the account
     * @param accountName login of the account
     * @param password passowrd of the account
     */
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

    /**
     * Callback for deleting certain account
     *
     * @param account account to be deleted
     */
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