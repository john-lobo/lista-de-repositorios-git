package com.jlndev.listaderepositriosgit.bases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.jlndev.listaderepositriosgit.MainActivity
import com.jlndev.listaderepositriosgit.R

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel>: Fragment() {

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding!!

    abstract val viewModel: VM

    lateinit var rootView: View

    abstract fun onGetToolbar(): ToolbarConfig
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
        rootView = binding.root
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInitViews()
        onInitViewModel()
    }

    override fun onStart() {
        super.onStart()
        val toolbarConfig = onGetToolbar()
        (requireActivity() as MainActivity).apply {
            findViewById<CoordinatorLayout>(R.id.appBarMainView).findViewById<TextView>(R.id.toolbarTitleView).text = toolbarConfig.title
            supportActionBar?.setDisplayHomeAsUpEnabled(toolbarConfig.showBackButton)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    data class ToolbarConfig(
        val title: String = "",
        val showBackButton: Boolean
    )
}