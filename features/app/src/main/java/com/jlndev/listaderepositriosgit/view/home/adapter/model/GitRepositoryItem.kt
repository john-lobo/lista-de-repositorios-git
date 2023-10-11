package com.jlndev.listaderepositriosgit.view.home.adapter.model

import com.jlndev.listaderepositriosgit.bases.BaseAdapter

data class GitRepositoryItem(
    override val id: String,
    val repositoryName: String,
    val stargazersCount: Int,
    val forksCount: Int,
    val avatarUrl: String,
    val ownerName: String,
    val viewType: Int = 0,
): BaseAdapter.BaseDiffItemView() {
    companion object {
        val LOADING = GitRepositoryItem("LOADING", "", 0, 0, "", "", 1)
    }
}
