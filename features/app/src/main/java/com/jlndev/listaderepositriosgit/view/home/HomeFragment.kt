package com.jlndev.listaderepositriosgit.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jlndev.baseservice.state.ResponseState
import com.jlndev.listaderepositriosgit.bases.BaseFragment
import com.jlndev.listaderepositriosgit.databinding.FragmentHomeBinding
import com.jlndev.listaderepositriosgit.utils.ext.gone
import com.jlndev.listaderepositriosgit.utils.ext.showLoading
import com.jlndev.listaderepositriosgit.utils.ext.toGitRepositoryItem
import com.jlndev.listaderepositriosgit.utils.ext.visible
import com.jlndev.listaderepositriosgit.view.home.adapter.GitRepositoriesAdapter
import com.jlndev.listaderepositriosgit.view.home.adapter.model.GitRepositoryItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val viewModel : HomeViewModel by viewModel()
    private lateinit var gitRepositoriesAdapter: GitRepositoriesAdapter

    override fun onInitData() {
        viewModel.searchRepositories()
    }

    override fun onGetToolbar(): ToolbarConfig = ToolbarConfig("Home", false)

    override fun onGetViewBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun onInitViewModel() {
        viewModel.repositoryGitLive.observe(viewLifecycleOwner) {
            when(it) {
                is ResponseState.Loading -> {
                    binding.loadingView.showLoading(it.isLoading)
                }
                is ResponseState.Success -> {
                    binding.recyclerGitRepositoriesView.visible()
                    val gitRepositoryItems = it.data.items.map { it.toGitRepositoryItem() }
                    gitRepositoriesAdapter.submitList(gitRepositoryItems)
                }

                is ResponseState.Error -> {
                    binding.recyclerGitRepositoriesView.gone()
                }
            }
        }
    }

    override fun onInitViews() {
        gitRepositoriesAdapter = GitRepositoriesAdapter(
            object : GitRepositoriesAdapter.RepositoryAdapterListener {
                override fun onRepositoryClicked(repository: GitRepositoryItem) {

                }
            }
        )

        binding.recyclerGitRepositoriesView.adapter = gitRepositoriesAdapter
    }
}