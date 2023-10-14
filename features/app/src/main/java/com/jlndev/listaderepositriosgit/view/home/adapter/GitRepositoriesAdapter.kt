package com.jlndev.listaderepositriosgit.view.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jlndev.listaderepositriosgit.R
import com.jlndev.coreandroid.bases.adapter.BaseAdapterController
import com.jlndev.coreandroid.bases.adapter.BaseAdapterListener
import com.jlndev.coreandroid.bases.adapter.BasePaginationScrollListener
import com.jlndev.coreandroid.bases.adapter.BaseViewHolder
import com.jlndev.listaderepositriosgit.databinding.ItemLoadingBinding
import com.jlndev.listaderepositriosgit.databinding.ItemRepositoryBinding
import com.jlndev.coreandroid.ext.loadImage
import com.jlndev.coreandroid.ext.setBoldSubstring
import com.jlndev.listaderepositriosgit.view.home.adapter.model.GitRepositoryItem

class GitRepositoriesAdapter(
    private val repositoriesAdapterListener: GitRepositoriesAdapterListener,
    private val context: Context
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

    inner class RepositoryItemViewHolder(private val itemBinding: ItemRepositoryBinding) : BaseViewHolder<GitRepositoryItem>(itemBinding.root) {
        override fun bind(item: GitRepositoryItem) {
            with(itemBinding) {
                context.apply {
                    itemRepositoryNameView.text = getString(R.string.repository_name_value, item.repositoryName).setBoldSubstring(getString(R.string.repository))
                    itemOwnerNameView.text = getString(R.string.owner_name_value, item.ownerName).setBoldSubstring(getString(R.string.owner_name))
                    itemTitleStarView.text = getString(R.string.stars_value, item.stargazersCount).setBoldSubstring(getString(R.string.stars))
                    itemTitleForkView.text = getString(R.string.forks_value, item.forksCount).setBoldSubstring(getString(R.string.forks))
                    itemImageview.loadImage(item.avatarUrl)
                }
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addOnScrollListener(object : BasePaginationScrollListener(recyclerView.layoutManager as LinearLayoutManager, context) {
            override fun loadMoreItems() {
                showLoadingMoreItems()
                repositoriesAdapterListener.loadMoreItems()
            }

            override fun isLoading() = repositoriesAdapterListener.isLoading()
        })
    }

    inner class RepositoryLoadingViewHolder(itemBinding: ItemLoadingBinding) : BaseViewHolder<GitRepositoryItem>(itemBinding.root) {
        override fun bind(item: GitRepositoryItem) {
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

    interface GitRepositoriesAdapterListener : BaseAdapterListener<GitRepositoryItem> {
        fun loadMoreItems()
        fun isLoading(): Boolean
    }
}
