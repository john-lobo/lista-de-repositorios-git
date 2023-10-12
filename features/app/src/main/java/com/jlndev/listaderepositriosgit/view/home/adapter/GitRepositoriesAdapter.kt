package com.jlndev.listaderepositriosgit.view.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.jlndev.listaderepositriosgit.R
import com.jlndev.listaderepositriosgit.bases.adapter.BaseAdapter
import com.jlndev.listaderepositriosgit.bases.adapter.BaseAdapterListener
import com.jlndev.listaderepositriosgit.bases.adapter.BasePaginationScrollListener
import com.jlndev.listaderepositriosgit.bases.adapter.BaseViewHolder
import com.jlndev.listaderepositriosgit.databinding.ItemLoadingBinding
import com.jlndev.listaderepositriosgit.databinding.ItemRepositoryBinding
import com.jlndev.listaderepositriosgit.view.home.adapter.model.GitRepositoryItem

class GitRepositoriesAdapter(
    private val repositoriesAdapterListener: GitRepositoriesAdapterListener,
    private val context: Context
) : BaseAdapter<GitRepositoryItem, BaseViewHolder<GitRepositoryItem>, BaseAdapterListener<GitRepositoryItem>>(repositoriesAdapterListener) {

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
            itemBinding.run {
                itemRepositoryNameView.text = "Repositório: ${item.repositoryName}"
                itemOwnerNameView.text = "Autor: ${item.ownerName}"
                itemTitleStarView.text = "Estrelas: ${item.stargazersCount}"
                itemTitleForkView.text = "Forks: ${item.forksCount}"

                Glide.with(itemView)
                    .load(item.avatarUrl)
                    .placeholder(R.drawable.ic_placeholder)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(itemImageview)
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
        return controller.getItems()[position].viewType
    }

    fun addMoreItems(gitRepositoryItems: List<GitRepositoryItem>) {
        controller.addAll(gitRepositoryItems)
        removeLoading()
    }

    fun removeLoading() {
        controller.removeItem(GitRepositoryItem.LOADING)
    }

    private fun showLoadingMoreItems() {
        controller.addItem(GitRepositoryItem.LOADING)
    }

    interface GitRepositoriesAdapterListener : BaseAdapterListener<GitRepositoryItem> {
        fun loadMoreItems()
        fun isLoading(): Boolean
    }

    companion object {
        private const val VIEW_TYPE_REPOSITORY = 0
        private const val VIEW_TYPE_LOADING = 1
    }
}
