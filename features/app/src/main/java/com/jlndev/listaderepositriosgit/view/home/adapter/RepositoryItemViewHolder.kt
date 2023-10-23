package com.jlndev.listaderepositriosgit.view.home.adapter

import com.jlndev.coreandroid.bases.adapter.BaseViewHolder
import com.jlndev.coreandroid.ext.loadImage
import com.jlndev.coreandroid.ext.setBoldSubstring
import com.jlndev.listaderepositriosgit.R
import com.jlndev.listaderepositriosgit.databinding.ItemRepositoryBinding
import com.jlndev.listaderepositriosgit.view.home.adapter.model.GitRepositoryItem

class RepositoryItemViewHolder(private val itemBinding: ItemRepositoryBinding) : BaseViewHolder<GitRepositoryItem>(itemBinding.root) {
    override fun bind(item: GitRepositoryItem) {
        with(itemBinding) {
            root.context.apply {
                itemRepositoryNameView.text = getString(R.string.repository_name_value, item.repositoryName).setBoldSubstring(getString(
                    R.string.repository))
                itemOwnerNameView.text = getString(R.string.owner_name_value, item.ownerName).setBoldSubstring(getString(
                    R.string.owner_name))
                itemTitleStarView.text = getString(R.string.stars_value, item.stargazersCount).setBoldSubstring(getString(
                    R.string.stars))
                itemTitleForkView.text = getString(R.string.forks_value, item.forksCount).setBoldSubstring(getString(
                    R.string.forks))
                itemImageview.loadImage(item.avatarUrl)
            }
        }
    }
}