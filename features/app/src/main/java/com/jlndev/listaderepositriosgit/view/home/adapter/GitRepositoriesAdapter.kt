package com.jlndev.listaderepositriosgit.view.home.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.jlndev.listaderepositriosgit.bases.BaseAdapter
import com.jlndev.listaderepositriosgit.bases.PaginationScrollListener
import com.jlndev.listaderepositriosgit.databinding.ItemLoadingBinding
import com.jlndev.listaderepositriosgit.databinding.ItemRepositoryBinding
import com.jlndev.listaderepositriosgit.utils.ext.gone
import com.jlndev.listaderepositriosgit.view.home.adapter.model.GitRepositoryItem

class GitRepositoriesAdapter(
    private val repositoriesAdapterListener: GitRepositoriesAdapterListener
) : BaseAdapter<GitRepositoryItem, BaseAdapter.ViewHolder<GitRepositoryItem>, BaseAdapter.AdapterListener<GitRepositoryItem>>(repositoriesAdapterListener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<GitRepositoryItem> {
        when (viewType) {
            VIEW_TYPE_REPOSITORY -> {
                val itemBinding = ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return RepositoryItemViewHolder(itemBinding)
            }
            VIEW_TYPE_LOADING -> {
                val itemBinding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return RepositoryLoadingViewHolder(itemBinding)
            }
            else -> throw IllegalArgumentException("Unknown viewType: $viewType")
        }
    }

    inner class RepositoryItemViewHolder(private val itemBinding: ItemRepositoryBinding) : ViewHolder<GitRepositoryItem>(itemBinding.root) {
        override fun bind(repository: GitRepositoryItem) {
            itemBinding.run {
                itemRepositoryNameView.text = "Repositório: ${repository.repositoryName}"
                itemOwnerNameView.text = "Autor: ${repository.ownerName}"
                itemTitleStarView.text =   "Estrelas: ${repository.stargazersCount}"
                itemTitleForkView.text =  "Forks: ${repository.forksCount}"
                Glide.with(itemView)
                    .load(repository.avatarUrl)
                    .fitCenter()
                    .listener(object : RequestListener<Drawable?> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable?>?, isFirstResource: Boolean): Boolean {
                            loadingItemView.gone()
                            return false
                        }
                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable?>?, dataSource: com.bumptech.glide.load.DataSource?, isFirstResource: Boolean): Boolean {
                            loadingItemView.gone()
                            return false
                        }
                    })
                    .into(itemImageview)
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addOnScrollListener(object : PaginationScrollListener(recyclerView.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                showLoadingMoreItems()
                repositoriesAdapterListener.loadMoreItems()
            }

            override fun isLoading() = repositoriesAdapterListener.isLoading()
        })
    }

    inner class RepositoryLoadingViewHolder(itemBinding: ItemLoadingBinding) : ViewHolder<GitRepositoryItem>(itemBinding.root) {
        override fun bind(item: GitRepositoryItem) {
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }

    fun addMoreItems(gitRepositoryItems: List<GitRepositoryItem>) {
        addAll(gitRepositoryItems)
        removeItem(GitRepositoryItem.LOADING)
    }

    private fun showLoadingMoreItems() {
        addItem(GitRepositoryItem.LOADING)
    }

    interface GitRepositoriesAdapterListener : AdapterListener<GitRepositoryItem> {
        fun loadMoreItems()
        fun isLoading(): Boolean
    }

    companion object {
        private const val VIEW_TYPE_REPOSITORY = 0
        private const val VIEW_TYPE_LOADING = 1
    }
}
