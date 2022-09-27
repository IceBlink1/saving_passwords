package com.example.savingpasswords.ui.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.savingpasswords.R
import com.example.savingpasswords.data.Account
import com.example.savingpasswords.databinding.FragmentMainBinding
import com.example.savingpasswords.util.BaseFragment
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * MainFragment represents main screen of the application
 * Contains state handling and event subscription
 *
 */
@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>(),
    Toolbar.OnMenuItemClickListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val adapter = GroupieAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect {
                    handleState(it)
                }
            }
        }

        binding.searchToolbar.setOnMenuItemClickListener(this)
        (binding.searchToolbar.menu.findItem(R.id.action_search).actionView as? SearchView)?.let {
            search(
                it
            )
        }
        binding.createButton.setOnClickListener {
            NewAccountBottomSheetFragment().show(parentFragmentManager, "NewAccount")
        }

        viewModel.init()
    }

    private fun handleState(state: MainViewModelState) {
        when {
            state.isLoading -> {
                showLoading()
            }
            state.error != null -> {
                showError(state.error)
            }
            state.accounts.isEmpty() -> {
                showEmpty()
            }
            else -> {
                showContent(state.accounts)
            }
        }
    }

    private fun showLoading() {
        binding.recyclerView.gone()
        binding.emptyMessage.gone()
        binding.errorMessage.gone()
        binding.createButton.gone()
        binding.searchToolbar.gone()
        binding.progressBar.visible()
    }

    private fun showEmpty() {
        binding.recyclerView.gone()
        binding.emptyMessage.visible()
        binding.errorMessage.gone()
        binding.progressBar.gone()
        binding.createButton.visible()
        binding.searchToolbar.visible()
    }

    private fun showError(throwable: Throwable) {
        binding.recyclerView.gone()
        binding.emptyMessage.gone()
        binding.errorMessage.visible()
        binding.progressBar.gone()
        binding.errorMessage.error = throwable.message
    }

    private fun showContent(accounts: List<Account>) {
        binding.recyclerView.visible()
        binding.emptyMessage.gone()
        binding.errorMessage.gone()
        binding.progressBar.gone()
        binding.createButton.visible()
        binding.searchToolbar.visible()

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter.clear()
        adapter.updateAsync(accounts.map { AccountItem(it) { account ->
            viewModel.delete(account)
        } })
    }

    private fun search(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.search(newText.orEmpty())
                return true
            }
        })
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_search -> {
                (item.actionView as? SearchView)?.let { search(it) }
            }
        }
        return false

    }

}

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}