package com.jlndev.listaderepositriosgit.bases.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.jlndev.listaderepositriosgit.bases.viewModel.BaseViewModel

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel>: Fragment() {

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding!!

    abstract val viewModel: VM

    abstract fun onInitData()
    abstract fun onGetViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB
    abstract fun onInitViews()
    abstract fun onInitViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onInitData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = onGetViewBinding(inflater, container)
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
}