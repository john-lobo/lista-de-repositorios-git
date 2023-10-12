package com.jlndev.listaderepositriosgit.utils.ext

import com.jlndev.githubservice.model.GithubRepositoryResponse
import com.jlndev.listaderepositriosgit.view.home.adapter.model.GitRepositoryItem

fun GithubRepositoryResponse.toGitRepositoryItem() : GitRepositoryItem {
    return GitRepositoryItem(
        id = this.id.toString(),
        repositoryName = repositoryName,
        stargazersCount = stargazersCount,
        forksCount = forksCount,
        avatarUrl = owner.avatarUrl,
        ownerName = owner.login
    )
}