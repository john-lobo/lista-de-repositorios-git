package com.jlndev.listaderepositriosgit.view.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jlndev.coreandroid.bases.adapter.BaseAdapterController
import com.jlndev.coreandroid.bases.adapter.BaseAdapterListener
import com.jlndev.coreandroid.bases.adapter.BasePaginationScrollListener
import com.jlndev.coreandroid.bases.adapter.BaseViewHolder
import com.jlndev.coreandroid.ext.isInternetAvailable
import com.jlndev.listaderepositriosgit.databinding.ItemLoadingBinding
import com.jlndev.listaderepositriosgit.databinding.ItemRepositoryBinding
import com.jlndev.listaderepositriosgit.view.home.adapter.model.GitRepositoryItem

class GitRepositoriesAdapter(
    private val repositoriesAdapterListener: GitRepositoriesAdapterListener
) : BaseAdapterController<GitRepositoryItem, BaseViewHolder<GitRepositoryItem>, BaseAdapterListener<GitRepositoryItem>>(repositoriesAdapterListener) {

    companion object {
        private const val VIEW_TYPE_REPOSITORY = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<GitRepositoryItem> {
        return when (viewType) {
            VIEW_TYPE_LOADING -> {
                val itemBinding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                RepositoryLoadingViewHolder(itemBinding)
            }
            else -> {
                val itemBinding = ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                RepositoryItemViewHolder(itemBinding)
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        if(recyclerView.context.isInternetAvailable()) {
            recyclerView.addOnScrollListener(object : BasePaginationScrollListener(recyclerView.layoutManager as LinearLayoutManager) {
                override fun loadMoreItems() {
                    showLoadingMoreItems()
                    repositoriesAdapterListener.loadMoreItems()
                }

                override fun isLoading() = repositoriesAdapterListener.isLoading()
            })
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }

    fun addMoreItems(gitRepositoryItems: List<GitRepositoryItem>) {
        addAll(gitRepositoryItems)
        removeLoading()
    }

    fun removeLoading() {
        removeItem(GitRepositoryItem.LOADING)
    }

    private fun showLoadingMoreItems() {
        addItem(GitRepositoryItem.LOADING)
    }
}
