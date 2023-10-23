package com.jlndev.listaderepositriosgit.view.home.adapter

import com.jlndev.coreandroid.bases.adapter.BaseViewHolder
import com.jlndev.coreandroid.ext.visible
import com.jlndev.listaderepositriosgit.databinding.ItemLoadingBinding
import com.jlndev.listaderepositriosgit.view.home.adapter.model.GitRepositoryItem

class RepositoryLoadingViewHolder(private val itemBinding: ItemLoadingBinding) : BaseViewHolder<GitRepositoryItem>(itemBinding.root) {
    override fun bind(item: GitRepositoryItem) {
        itemBinding.loadingItemView.visible()
    }
}
