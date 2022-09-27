package com.example.savingpasswords.ui.main

import android.view.View
import com.example.savingpasswords.R
import com.example.savingpasswords.data.Account
import com.example.savingpasswords.databinding.AccountItemBinding
import com.xwray.groupie.viewbinding.BindableItem

/**
 * UI representation of an Account for RecyclerView
 *
 * @property account
 * @property onDeleteButtonPressed
 */
class AccountItem(
    private val account: Account,
    private val onDeleteButtonPressed: (Account) -> Unit
) : BindableItem<AccountItemBinding>() {

    override fun bind(viewBinding: AccountItemBinding, position: Int) {
        viewBinding.domainTextView.text = account.domain
        viewBinding.loginTextView.text = account.login
        viewBinding.passwordTextView.text = account.password
        viewBinding.deleteButton.setOnClickListener { onDeleteButtonPressed(account) }
    }

    override fun getLayout(): Int {
        return R.layout.account_item
    }

    override fun initializeViewBinding(view: View): AccountItemBinding {
        return AccountItemBinding.bind(view)
    }


}