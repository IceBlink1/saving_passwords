package com.example.savingpasswords.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.savingpasswords.databinding.FragmentCreateBinding
import com.example.savingpasswords.util.BaseBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.subscribe

@AndroidEntryPoint
class NewAccountBottomSheetFragment :
    BaseBottomSheetFragment<FragmentCreateBinding, NewAccountViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect {
                    handleState(it)
                }
            }
        }
        binding.submitButton.setOnClickListener {
            with(binding) {
                viewModel.create(
                    domainEditText.text.toString(),
                    loginEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
        }
    }

    private fun handleState(state: NewAccountState) {
        when {
            state.created -> {
                dismiss()
            }
            state.error != null -> {
                Toast.makeText(requireContext(), "Error occurred", Toast.LENGTH_LONG).show()
            }
        }
    }

}