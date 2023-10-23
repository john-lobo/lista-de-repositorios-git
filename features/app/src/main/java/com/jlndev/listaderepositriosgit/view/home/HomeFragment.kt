package com.jlndev.listaderepositriosgit.view.home

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.jlndev.baseservice.state.ResponseState
import com.jlndev.coreandroid.bases.fragment.BaseFragment
import com.jlndev.coreandroid.ext.gone
import com.jlndev.coreandroid.ext.showLoading
import com.jlndev.coreandroid.ext.visible
import com.jlndev.listaderepositriosgit.R
import com.jlndev.listaderepositriosgit.databinding.FragmentHomeBinding
import com.jlndev.listaderepositriosgit.view.ext.toGitRepositoryItem
import com.jlndev.listaderepositriosgit.view.home.adapter.GitRepositoriesAdapter
import com.jlndev.listaderepositriosgit.view.home.adapter.GitRepositoriesAdapterListener
import com.jlndev.listaderepositriosgit.view.home.adapter.model.GitRepositoryItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    companion object {
        const val KEY_GIT_REPOSITORY_ITEM = "KEY_GIT_REPOSITORY_ITEM"
    }

    override val viewModel : HomeViewModel by viewModel()
    private lateinit var gitRepositoriesAdapter: GitRepositoriesAdapter
    private  var isLoading = false

    override fun onInitData() {
        viewModel.loadFirstPageItems()
    }

    override fun onGetViewBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun onInitViews() {
        gitRepositoriesAdapter = GitRepositoriesAdapter(object : GitRepositoriesAdapterListener {
            override fun onAdapterItemClicked(position: Int, item: GitRepositoryItem, view: View?) {
                findNavController().navigate(R.id.action_home_to_details, bundleOf(KEY_GIT_REPOSITORY_ITEM to item))
            }

            override fun loadMoreItems() {
                viewModel.loadMoreItems()
            }

            override fun isLoading() = isLoading
        })

        binding.recyclerGitRepositoriesView.adapter = gitRepositoriesAdapter

        binding.errorView.retryButton.setOnClickListener {
            onInitData()
        }

        binding.swipeRefreshView.setOnRefreshListener {
            binding.swipeRefreshView.isRefreshing = false
            clearAndUpdateAlertView()
        }
    }

    override fun onInitViewModel() {
        viewModel.repositoryFirstPageLive.observe(viewLifecycleOwner) {
            when(it) {
                is ResponseState.Success -> {
                    binding.recyclerGitRepositoriesView.visible()
                    val gitRepositoryItems = it.data.items.map { it.toGitRepositoryItem() }
                        .sortedByDescending { it.stargazersCount }
                    gitRepositoriesAdapter.submitList(gitRepositoryItems)
                }

                is ResponseState.Error -> {
                    showErrorView()
                }
            }
        }

        viewModel.repositoryMorePageLive.observe(viewLifecycleOwner) {
            when(it) {
                is ResponseState.Success -> {
                    val gitRepositoryItems = it.data.items.map { it.toGitRepositoryItem() }
                        .sortedByDescending { it.stargazersCount }
                    gitRepositoriesAdapter.addMoreItems(gitRepositoryItems)
                }

                is ResponseState.Error -> {
                    gitRepositoriesAdapter.removeLoading()
                }
            }
        }

        viewModel.updateRepositoryLive.observe(viewLifecycleOwner) {
            when(it) {
                is ResponseState.Success -> {
                    gitRepositoriesAdapter.clear()
                    val gitRepositoryItems = it.data.items.map { it.toGitRepositoryItem() }
                        .sortedByDescending { it.stargazersCount }
                    gitRepositoriesAdapter.submitList(gitRepositoryItems)
                    binding.recyclerGitRepositoriesView.visible()
                }

                is ResponseState.Error -> {
                    showErrorView()
                }
            }
        }

        viewModel.repositoryFirstPageLoading.observe(viewLifecycleOwner) {
            if(it) {
                binding.recyclerGitRepositoriesView.gone()
                binding.errorView.root.gone()
            }
            binding.loadingView.showLoading(it)
        }

        viewModel.repositoryMorePageLoading.observe(viewLifecycleOwner) {
            isLoading = it
        }

        viewModel.updateRepositoryLoadingLive.observe(viewLifecycleOwner) {
            binding.swipeRefreshView.isRefreshing = it
        }
    }

    private fun clearAndUpdateAlertView() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.attention))
            .setMessage(getString(R.string.delete_and_update_list))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                viewModel.clearAndSearchFirstRepositories()
            }
            .setNegativeButton(getString(R.string.no)) { dismiss, _ ->
                dismiss.dismiss()
            }
            .create()
            .show()
    }

    private fun showErrorView() {
        binding.recyclerGitRepositoriesView.gone()
        binding.errorView.root.visible()
    }
}