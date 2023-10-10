package com.montapp.montapp.common.bases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.jlndev.listaderepositriosgit.bases.BaseViewModel
import com.montapp.coreandroid.bases.ext.isValidInput
import com.montapp.coreandroid.bases.ext.showSnackbar

abstract class BaseBottomSheetFragment<VB : ViewBinding, VM : BaseViewModel>: BottomSheetDialogFragment() {

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding!!

    abstract val viewModel: VM

    lateinit var rootView: View

    abstract fun onInitData()
    abstract fun onGetBinding(inflater: LayoutInflater, container: ViewGroup?): VB
    abstract fun onInitViews()
    abstract fun onInitViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onInitData()
    }

    override fun getTheme(): Int {
        return com.montapp.coreandroid.R.style.BottomSheetDialogTheme
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = onGetBinding(inflater, container)
        rootView = binding.root
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInitViews()
        onInitViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun areAllFieldsValid(vararg fields: TextInputEditText): Boolean {
        return fields.all { it.isValidInput() }
    }
    fun areAllFieldsValid(inputViews: List<TextInputEditText>): Boolean {
        return inputViews.all { it.isValidInput() }
    }

    fun showError(message: String,actionText: String? = null, view: View? = null ,action: (() -> Unit)? = null) {
        rootView.showSnackbar(message, actionText, action, view)
    }

}