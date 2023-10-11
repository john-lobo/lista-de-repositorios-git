package com.jlndev.listaderepositriosgit.utils.ext

import com.jlndev.githubservice.model.GitRepositoryResponse
import com.jlndev.listaderepositriosgit.view.home.adapter.model.GitRepositoryItem

fun GitRepositoryResponse.toGitRepositoryItem() : GitRepositoryItem {
    return GitRepositoryItem(
        id = this.id.toString(),
        repositoryName =  this.name,
        stargazersCount = stargazersCount,
        forksCount = forksCount,
        avatarUrl = owner.avatarUrl,
        ownerName = owner.login
    )
}