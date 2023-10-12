package com.jlndev.listaderepositriosgit.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.jlndev.baseservice.state.ResponseState
import com.jlndev.listaderepositriosgit.R
import com.jlndev.listaderepositriosgit.bases.fragment.BaseFragment
import com.jlndev.listaderepositriosgit.databinding.FragmentHomeBinding
import com.jlndev.listaderepositriosgit.utils.ext.gone
import com.jlndev.listaderepositriosgit.utils.ext.showLoading
import com.jlndev.listaderepositriosgit.utils.ext.toGitRepositoryItem
import com.jlndev.listaderepositriosgit.utils.ext.visible
import com.jlndev.listaderepositriosgit.view.home.adapter.GitRepositoriesAdapter
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
        gitRepositoriesAdapter = GitRepositoriesAdapter(object : GitRepositoriesAdapter.GitRepositoriesAdapterListener {
            override fun onAdapterItemClicked(position: Int, item: GitRepositoryItem, view: View?) {
                findNavController().navigate(R.id.action_home_to_details, bundleOf(KEY_GIT_REPOSITORY_ITEM to item))
            }

            override fun loadMoreItems() {
                viewModel.loadMoreItems()
            }

            override fun isLoading() = isLoading
        }, requireContext())

        binding.recyclerGitRepositoriesView.adapter = gitRepositoriesAdapter

        binding.errorView.retryButton.setOnClickListener {
            onInitData()
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
                    binding.recyclerGitRepositoriesView.gone()
                    binding.errorView.root.visible()
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
    }
}