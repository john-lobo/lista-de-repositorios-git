package com.jlndev.listaderepositriosgit.view.home.adapter

import com.jlndev.coreandroid.bases.adapter.BaseAdapterListener
import com.jlndev.listaderepositriosgit.view.home.adapter.model.GitRepositoryItem

interface GitRepositoriesAdapterListener : BaseAdapterListener<GitRepositoryItem> {
    fun loadMoreItems()
    fun isLoading(): Boolean
}