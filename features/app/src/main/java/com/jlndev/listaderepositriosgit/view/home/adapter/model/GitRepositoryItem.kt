package com.jlndev.listaderepositriosgit.view.home.adapter.model


data class GitRepositoryItem(
    val repositoryId: String,
    val repositoryName: String,
    val stargazersCount: Int,
    val forksCount: Int,
    val avatarUrl: String,
    val ownerName: String
)
